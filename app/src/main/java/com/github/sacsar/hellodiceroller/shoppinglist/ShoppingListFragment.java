package com.github.sacsar.hellodiceroller.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sacsar.hellodiceroller.databinding.ShoppingListFragmentBinding;
import com.github.sacsar.hellodiceroller.recyclerview.ItemTouchCallback;
import com.github.sacsar.hellodiceroller.shoppinglist.model.ShoppingListItem;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Inject;

@AndroidEntryPoint
public class ShoppingListFragment extends Fragment {

  private static final String TAG = "ShoppingListFragment";

  private ShoppingListViewModel viewModel;
  @Inject ShoppingListAdapter adapter;
  private RecyclerView recyclerView;
  private ShoppingListFragmentBinding binding;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = ShoppingListFragmentBinding.inflate(inflater, container, false);

    recyclerView = binding.shoppingListRecyclerView;
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    ItemTouchCallback<ShoppingListAdapter> callback = new ItemTouchCallback<>(adapter);
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
    itemTouchHelper.attachToRecyclerView(recyclerView);

    binding.addItemFab.setOnClickListener(
        l -> {
          ShoppingListFragmentDirections.ActionShoppingListFragmentToAddItemFragment action =
              ShoppingListFragmentDirections.actionShoppingListFragmentToAddItemFragment(
                  adapter.getItemCount());
          NavHostFragment.findNavController(ShoppingListFragment.this).navigate(action);
        });

    return binding.getRoot();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

    viewModel
        .shoppingListItems()
        .observe(
            this,
            items -> {
              Log.v(TAG, "Updating shopping list item adapter");
              adapter.submitList(items);
            });

    compositeDisposable.add(viewModel
        .deletedItem()
        .subscribe(() -> {
                Log.v(TAG, "item deleted");
                displayDeleteSnackbar();
        }));
  }

  private void displayDeleteSnackbar() {
    Log.v(TAG, "display snackbar");
    Snackbar.make(binding.shoppingListCoordinator, "Deleted", Snackbar.LENGTH_LONG)
        .setAction(
            "Undo",
                view -> viewModel.undelete())
        .show();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    compositeDisposable.dispose();
    recyclerView.setAdapter(null);
  }
}
