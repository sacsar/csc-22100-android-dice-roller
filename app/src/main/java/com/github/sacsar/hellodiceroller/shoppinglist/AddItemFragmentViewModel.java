package com.github.sacsar.hellodiceroller.shoppinglist;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class AddItemFragmentViewModel extends ViewModel {
  private static final String TAG = "AddItemViewModel";

  ShoppingListRepository repository;

  @Inject
  public AddItemFragmentViewModel(ShoppingListRepository shoppingListRepository) {
    repository = shoppingListRepository;
  }

  public void addItem(String s, int position) {
    Log.d(TAG, String.format("Adding %s at position %s", s, position));
    repository
        .addItem(ShoppingListItem.create(s, position, false))
        .subscribe(
            () -> Log.d(TAG, "Item added successuflly"),
            (err) -> Log.e(TAG, "Failed to add item", err));
  }
}
