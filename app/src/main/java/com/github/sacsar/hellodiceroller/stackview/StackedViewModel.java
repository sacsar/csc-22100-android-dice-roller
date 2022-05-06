package com.github.sacsar.hellodiceroller.stackview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@HiltViewModel
public class StackedViewModel extends ViewModel {

  private final MutableLiveData<List<Integer>> items;

  @Inject
  public StackedViewModel() {
    items = new MutableLiveData<>(Arrays.asList(1, 2, 3, 4, 5));
  }

  public LiveData<List<Integer>> items() {
    return items;
  }
}
