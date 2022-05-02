package com.github.sacsar.hellodiceroller.shoppinglist;

import android.util.Log;
import com.github.sacsar.hellodiceroller.shoppinglist.dao.ShoppingListDao;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShoppingListRepository {

  private static final String TAG = "ShoppingListRepository";

  @Inject ShoppingListDao dao;

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
    return dao.delete(item).subscribeOn(Schedulers.io());
  }

  public Flowable<List<ShoppingListItem>> getAllItems() {
    return dao.getAllItems().subscribeOn(Schedulers.io());
  }

  public Flowable<List<ShoppingListItem>> getIncompleteItems() {
    return dao.getIncompleteItems().subscribeOn(Schedulers.io());
  }

  @Inject
  public ShoppingListRepository() {}
}
