package com.github.sacsar.hellodiceroller.shoppinglist;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class ShoppingListViewModel extends ViewModel {
  private static final String TAG = "ShoppingListViewModel";
  private final ShoppingListRepository repository;

  private final MutableLiveData<List<ShoppingListItem>> shoppingListItems = new MutableLiveData<>();

  private final CompositeDisposable disposable = new CompositeDisposable();

  @Inject
  public ShoppingListViewModel(ShoppingListRepository repository) {
    this.repository = repository;

    // This is where we subscribe to the repository
    disposable.add(repository
        .getAllItems()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(shoppingListItems::setValue));
  }

  public Completable deletedItem() {
    // convert to a completable because we don't want to tell the view what
    // was deleted, only that it should display the snackbar
    // https://stackoverflow.com/a/56326786/424173 -- don't wait for deletedItems to complete, because it doesn't
    return repository.deletedItems().firstOrError().ignoreElement();
  }

  public LiveData<List<ShoppingListItem>> shoppingListItems() {
    return shoppingListItems;
  }

  public void undelete() {
    Log.v(TAG, "Received undelete from fragment");
    repository.undelete();
  }

  @Override
  public void onCleared() {
    disposable.dispose();
  }
}
