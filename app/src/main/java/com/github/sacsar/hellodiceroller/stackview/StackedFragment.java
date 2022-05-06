package com.github.sacsar.hellodiceroller.stackview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.sacsar.hellodiceroller.R;
import com.github.sacsar.hellodiceroller.databinding.StackedFragmentBinding;

public class StackedFragment extends Fragment {

    private StackedFragmentBinding binding;
    private StackedViewModel viewModel;
    private StackViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StackedViewModel.class);


        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StackedFragmentBinding.inflate(inflater, container, false);
        adapter = new StackViewAdapter(requireContext(), R.layout.card, R.id.cardText);
        // TODO: Figure out a better example here -- this re-adds everything in the viewModel.items() list
        // even if it was there already
        viewModel.items().observe(this, items -> adapter.addAll(items));

        binding.stackView.setAdapter(adapter);
        return binding.getRoot();
    }
}