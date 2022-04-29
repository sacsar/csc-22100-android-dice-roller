package com.github.sacsar.hellodiceroller.shoppinglist;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.github.sacsar.hellodiceroller.shoppinglist.dao.ShoppingListDao;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;

@Database(
    entities = {ShoppingListItem.class},
    version = 1)
public abstract class ShoppingListDatabase extends RoomDatabase {
  public abstract ShoppingListDao shoppingListDao();
}
