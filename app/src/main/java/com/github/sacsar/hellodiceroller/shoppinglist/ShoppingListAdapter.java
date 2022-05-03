package com.github.sacsar.hellodiceroller.shoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sacsar.hellodiceroller.databinding.ShoppingListItemBinding;
import com.github.sacsar.hellodiceroller.recyclerview.WithItemDismiss;
import com.github.sacsar.hellodiceroller.recyclerview.WithItemMove;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import dagger.hilt.android.scopes.FragmentScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@FragmentScoped
public class ShoppingListAdapter
    extends ListAdapter<ShoppingListItem, ShoppingListAdapter.ViewHolder>
    implements WithItemDismiss, WithItemMove {
  private static final String TAG = "ShoppingListAdapter";

  private final ShoppingListRepository shoppingListRepository;

  @Inject
  public ShoppingListAdapter(
      @NonNull @NotNull ShoppingListItemDiff diffCallback,
      ShoppingListRepository shoppingListRepository) {
    super(diffCallback);
    this.shoppingListRepository = shoppingListRepository;
  }

  @NonNull
  @NotNull
  @Override
  public ShoppingListAdapter.ViewHolder onCreateViewHolder(
      @NonNull @NotNull ViewGroup parent, int viewType) {
    return ViewHolder.create(parent);
  }

  @Override
  public void onBindViewHolder(
      @NonNull @NotNull ShoppingListAdapter.ViewHolder holder, int position) {
    // here is where we bind our things
    ShoppingListItem item = getItem(position);
    holder.bind(item, shoppingListRepository);
  }

  @Override
  public void onItemDismiss(int position) {
    // we're deleting an item -- I do want this to come back to the main view model to ask for
    // confirmation
    Log.d(TAG, String.format("On item dismiss called for position %s", position));
  }

  @Override
  public void withItemMove(int startPosition, int endPosition) {
    List<ShoppingListItem> currentList = getCurrentList();
    List<ShoppingListItem> updatedList = new ArrayList<>(currentList.size());
    ShoppingListItem movedItem = null;
    if (startPosition < endPosition) {
      for (int i = 0; i < currentList.size(); i++) {
        if (i < startPosition) {
          updatedList.add(currentList.get(i));
        } else if (i == startPosition) {
          movedItem = currentList.get(i).withPosition(endPosition);
        } else if (i < endPosition) {
          updatedList.add(currentList.get(i).withPosition(i - 1));
        } else if (i == endPosition) {
          updatedList.add(movedItem);
        } else {
          updatedList.add(currentList.get(i));
        }
      }
    } else if (startPosition > endPosition) {
      for (int i = 0; i < currentList.size(); i ++) {
        if ( i < endPosition) {
          updatedList.add(currentList.get(i));

        }
      }
    } else {
      updatedList = currentList;
    }
    submitList(currentList, () -> shoppingListRepository.updateItems());
    Log.d(TAG, String.format("Moving from position %s to position %s", startPosition, endPosition));
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final ShoppingListItemBinding binding;

    public ViewHolder(@NonNull ShoppingListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(ShoppingListItem item, ShoppingListRepository repository) {
      binding.shoppingListItemText.setText(item.item());
      binding.checkBox.setChecked(item.completed());
      binding.checkBox.setOnCheckedChangeListener(
          (compoundButton, isChecked) -> {
            // NOTE: We *do* need to subscribe for the action to actually occur.
            if (isChecked) {
              repository.completeItem(item).subscribe();
            } else {
              repository.unsetCompleteItem(item).subscribe();
            }
          });
    }

    public static ViewHolder create(ViewGroup parent) {
      ShoppingListItemBinding binding =
          ShoppingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      return new ViewHolder(binding);
    }
  }

  public static class ShoppingListItemDiff extends DiffUtil.ItemCallback<ShoppingListItem> {

    public ShoppingListItemDiff() {}

    @Override
    public boolean areItemsTheSame(
        @NonNull @NotNull ShoppingListItem oldItem, @NonNull @NotNull ShoppingListItem newItem) {
      return Objects.equals(oldItem.id(), newItem.id());
    }

    @Override
    public boolean areContentsTheSame(
        @NonNull @NotNull ShoppingListItem oldItem, @NonNull @NotNull ShoppingListItem newItem) {
      return Objects.equals(oldItem.item(), newItem.item())
          && Objects.equals(oldItem.completed(), newItem.completed());
    }
  }
}
