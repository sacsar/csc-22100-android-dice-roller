package com.github.sacsar.hellodiceroller.shoppinglist;

import android.util.Log;
import com.github.sacsar.hellodiceroller.shoppinglist.dao.ShoppingListDao;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.single.SingleJust;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShoppingListRepository {

  private static final String TAG = "ShoppingListRepository";

  private final Subject<ShoppingListItem> itemUpdateBus;
  private final Subject<ShoppingListItem> deleteItemBus;
  private final ShoppingListDao dao;
  private final CompositeDisposable disposable = new CompositeDisposable();

  public Completable addItem(ShoppingListItem item) {
    return dao.insert(item).subscribeOn(Schedulers.io());
  }

  public Completable completeItem(ShoppingListItem item) {
    Log.d(TAG, String.format("Marking %s as completed", item));
    return dao.update(item.getCompletedItem()).subscribeOn(Schedulers.io());
  }

  public Completable unsetCompleteItem(ShoppingListItem item) {
    Log.d(TAG, String.format("Marking %s as incomplete", item));
    return dao.update(item.toggleCompleted()).subscribeOn(Schedulers.io());
  }

  public Completable deleteItem(ShoppingListItem item) {
    Log.d(TAG, String.format("Deleting item %s", item));
    Completable deleteCompletable = dao.delete(item).subscribeOn(Schedulers.io());
    disposable.add(deleteCompletable.subscribe(() -> deleteItemBus.onNext(item)));
    return deleteCompletable;
  }

  public Flowable<List<ShoppingListItem>> getAllItems() {
    return dao.getAllItems().subscribeOn(Schedulers.io());
  }

  public Flowable<List<ShoppingListItem>> getIncompleteItems() {
    return dao.getIncompleteItems().subscribeOn(Schedulers.io());
  }

  public void updateItem(ShoppingListItem item) {
    itemUpdateBus.onNext(item);
  }

  public void updateItems(ShoppingListItem... items) {
    for (ShoppingListItem item : items) {
      itemUpdateBus.onNext(item);
    }
  }

  public Observable<ShoppingListItem> deletedItems() {
    return deleteItemBus;
  }

  @Inject
  public ShoppingListRepository(ShoppingListDao dao) {
    this.dao = dao;
    this.itemUpdateBus = PublishSubject.create();
    this.deleteItemBus = BehaviorSubject.create();
    subscribeToItemUpdates();
    disposable.add(deleteItemBus.subscribe(item -> {
      Log.v(TAG, String.format("Delete item bus sees %s has been deleted", item));
    }));
  }

  private void subscribeToItemUpdates() {
    disposable.add(
        itemUpdateBus
            // make sure we're off the main thread before we go to the DB (subscribeOn at the end
            // didn't work)
            .observeOn(Schedulers.io())
            .distinctUntilChanged()
            .flatMapCompletable(dao::update)
            .subscribe());
  }

  public void undelete() {
    // when you subscribe to a behavior subject, you immediately get the last item emitted
    // I do think there's a race condition here, frankly
    disposable.add(deleteItemBus.observeOn(Schedulers.io())
            .subscribe(dao::insert));
  }
}
