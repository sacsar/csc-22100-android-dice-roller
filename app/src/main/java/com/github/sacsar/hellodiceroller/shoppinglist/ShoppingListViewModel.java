package com.github.sacsar.hellodiceroller.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class ShoppingListViewModel extends ViewModel {

  private ShoppingListRepository repository;

  private final MutableLiveData<List<ShoppingListItem>> shoppingListItems = new MutableLiveData<>();

  @Inject
  public ShoppingListViewModel(ShoppingListRepository repository) {
    this.repository = repository;

    // This is where we subscribe to the repository
    repository
        .getAllItems()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(shoppingListItems::setValue);
  }

  public LiveData<List<ShoppingListItem>> shoppingListItems() {
    return shoppingListItems;
  }
}
