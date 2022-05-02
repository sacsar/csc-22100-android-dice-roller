package com.github.sacsar.hellodiceroller.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.sacsar.hellodiceroller.databinding.AddItemFragmentBinding;
import dagger.hilt.android.AndroidEntryPoint;
import org.jetbrains.annotations.NotNull;

@AndroidEntryPoint
public class AddItemFragment extends DialogFragment {

  AddItemFragmentViewModel viewModel;
  AddItemFragmentBinding binding;

  @NotNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    super.onCreateDialog(savedInstanceState);
    // retrieve the current number of items off the nav arguments
    AddItemFragmentArgs args = AddItemFragmentArgs.fromBundle(getArguments());
    int numItems = args.getNumItems();

    viewModel = new ViewModelProvider(this).get(AddItemFragmentViewModel.class);

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
    binding = AddItemFragmentBinding.inflate(LayoutInflater.from(getContext()), null, false);

    dialogBuilder
        .setView(binding.getRoot())
        .setTitle("Add Item")
        .setNegativeButton("Cancel", (id, l) -> dismiss())
        .setPositiveButton(
            "OK",
            (id, l) -> {
              addItem(numItems);
            });

    return dialogBuilder.create();
  }

  private void addItem(int position) {
    viewModel.addItem(binding.itemText.getText().toString(), position);
  }
}
