package com.github.sacsar.hellodiceroller.shoppinglist.dao;

import androidx.room.*;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

@Dao
public interface ShoppingListDao {
  @Insert
  Completable insert(ShoppingListItem... items);

  @Update
  Completable update(ShoppingListItem item);

  @Delete
  Completable delete(ShoppingListItem... items);

  @Query("SELECT * FROM shopping_list_items")
  Flowable<List<ShoppingListItem>> getAllItems();

  @Query("SELECT * FROM shopping_list_items WHERE completed = 0")
  Flowable<List<ShoppingListItem>> getIncompleteItems();
}
