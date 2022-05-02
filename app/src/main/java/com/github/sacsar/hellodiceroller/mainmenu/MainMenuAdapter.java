package com.github.sacsar.hellodiceroller.mainmenu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sacsar.hellodiceroller.databinding.MainMenuItemBinding;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class MainMenuAdapter extends ListAdapter<MainMenuItem, MainMenuAdapter.ViewHolder> {
  private static final String TAG = "MainMenuAdapter";

  private final NavController navController;

  protected MainMenuAdapter(
      @NonNull @NotNull DiffUtil.ItemCallback<MainMenuItem> diffCallback,
      NavController navController) {
    super(diffCallback);
    this.navController = navController;
  }

  @NonNull
  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    return ViewHolder.create(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
    MainMenuItem item = getItem(position);
    Log.i(TAG, String.format("Binding view holder for %s", item));
    holder.binding.menuItemItext.setText(item.displayString());
    holder.itemView.setOnClickListener(l -> navController.navigate(item.menuToTargetAction()));
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    MainMenuItemBinding binding;

    public ViewHolder(MainMenuItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public static ViewHolder create(ViewGroup parent) {
      MainMenuItemBinding binding =
          MainMenuItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      return new ViewHolder(binding);
    }
  }

  public static class MenuItemDiff extends DiffUtil.ItemCallback<MainMenuItem> {

    @Override
    public boolean areItemsTheSame(
        @NonNull @NotNull MainMenuItem oldItem, @NonNull @NotNull MainMenuItem newItem) {
      return Objects.equals(oldItem.displayString(), newItem.displayString());
    }

    @Override
    public boolean areContentsTheSame(
        @NonNull @NotNull MainMenuItem oldItem, @NonNull @NotNull MainMenuItem newItem) {
      return Objects.equals(oldItem.displayString(), newItem.displayString());
    }
  }
}
