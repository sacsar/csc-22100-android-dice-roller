package com.github.sacsar.hellodiceroller.shoppinglist.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.auto.value.AutoValue;

/**
 * The database model for our shopping list items.
 *
 * <p>A few things to note: - This use the AutoValue-based classes (see
 * https://developer.android.com/training/data-storage/room/defining-data#autovalue) -
 * Unfortunately, that means we have to use the CopyAnnotation annotation over and over.
 */
@AutoValue
@Entity(tableName = "shopping_list_items")
public abstract class ShoppingListItem {

  @AutoValue.CopyAnnotations
  @PrimaryKey(autoGenerate = true)
  public abstract int id();

  @AutoValue.CopyAnnotations
  public abstract String item();

  @AutoValue.CopyAnnotations
  public abstract int position();

  @AutoValue.CopyAnnotations
  public abstract boolean completed();

  @Ignore
  public ShoppingListItem getCompletedItem() {
    return ShoppingListItem.create(id(), item(), position(), true);
  }

  @Ignore
  public ShoppingListItem toggleCompleted() {
    return ShoppingListItem.create(id(), item(), position(), !completed());
  }

  public static ShoppingListItem create(int id, String item, int position, boolean completed) {
    return new AutoValue_ShoppingListItem(id, item, position, completed);
  }

  @Ignore
  public static ShoppingListItem create(String item, int positionId, boolean completed) {
    return new AutoValue_ShoppingListItem(0, item, positionId, completed);
  }
}
