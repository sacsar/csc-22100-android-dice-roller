package com.github.sacsar.hellodiceroller.recyclerview;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

// See
// https://github.com/iPaulPro/Android-ItemTouchHelper-Demo/blob/d164fba0f27c8aa38cfa7dbd4bc74d53dea44605/app/src/main/java/co/paulburke/android/itemtouchhelperdemo/helper/SimpleItemTouchHelperCallback.java#L34
public class ItemTouchCallback<T extends RecyclerView.Adapter<?> & WithItemMove & WithItemDismiss>
    extends ItemTouchHelper.SimpleCallback {

  private static final String TAG = "ItemTouchCallback";

  private static final int DRAG_DIRS = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
  private static final int SWIPE_DIRS = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

  private boolean dragInProgress = false;

  private T adapter;

  @Inject
  public ItemTouchCallback(T adapter) {
    super(DRAG_DIRS, SWIPE_DIRS);
    this.adapter = adapter;
  }

  @Override
  public boolean onMove(
      @NonNull @NotNull RecyclerView recyclerView,
      @NonNull @NotNull RecyclerView.ViewHolder viewHolder,
      @NonNull @NotNull RecyclerView.ViewHolder target) {
    if (viewHolder.getItemViewType() != target.getItemViewType()) {
      Log.d(TAG, "Item move, mismatched view types");
      return false;
    }
    adapter.withItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    Log.d(TAG, "Item move detected!");
    return true;
  }

  @Override
  public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
    adapter.onItemDismiss(viewHolder.getAdapterPosition());
  }

  @Override
  public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    super.onSelectedChanged(viewHolder, actionState);
    if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
      Log.d(TAG, "Selected changed -- do we trigger this from swipe?"); // Yes, we do
    } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
      dragInProgress = true;
    }
  }
}
