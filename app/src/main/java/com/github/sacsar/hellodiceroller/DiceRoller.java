package com.github.sacsar.hellodiceroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.sacsar.hellodiceroller.databinding.DiceRollerFragmentBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiceRoller extends Fragment {

  private DiceRollerViewModel viewModel;
  private DiceRollerFragmentBinding binding;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = new ViewModelProvider(this).get(DiceRollerViewModel.class);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DiceRollerFragmentBinding.inflate(inflater, container, false);

    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
