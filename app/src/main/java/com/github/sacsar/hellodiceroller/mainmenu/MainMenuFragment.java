package com.github.sacsar.hellodiceroller.mainmenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sacsar.hellodiceroller.databinding.MainMenuFragmentBinding;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;
import javax.inject.Inject;

@AndroidEntryPoint
public class MainMenuFragment extends Fragment {
  private static final String TAG = "MainMenuFragment";

  @Inject List<MainMenuItem> menuItems;

  private MainMenuAdapter mainMenuAdapter;
  private RecyclerView recyclerView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    menuItems.forEach(item -> Log.i(TAG, item.displayString()));
    mainMenuAdapter =
        new MainMenuAdapter(
            new MainMenuAdapter.MenuItemDiff(), NavHostFragment.findNavController(this));
    mainMenuAdapter.submitList(menuItems);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    com.github.sacsar.hellodiceroller.databinding.MainMenuFragmentBinding binding =
        MainMenuFragmentBinding.inflate(inflater, container, false);
    recyclerView = binding.mainMenuRecyclerView;
    recyclerView.setAdapter(mainMenuAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    Log.i(TAG, String.format("Num menu items: %s", recyclerView.getAdapter().getItemCount()));
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    recyclerView.setAdapter(null);
  }
}
