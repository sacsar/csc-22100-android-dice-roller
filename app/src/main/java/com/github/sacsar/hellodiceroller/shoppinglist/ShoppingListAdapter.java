package com.github.sacsar.hellodiceroller.shoppinglist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sacsar.hellodiceroller.databinding.ShoppingListItemBinding;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import dagger.hilt.android.scopes.FragmentScoped;
import java.util.Objects;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@FragmentScoped
public class ShoppingListAdapter
    extends ListAdapter<ShoppingListItem, ShoppingListAdapter.ViewHolder> {

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
