package com.github.sacsar.hellodiceroller;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class DiceRollerViewModel extends ViewModel {
  // Hilt view models need an Inject-annotated constructor.
  @Inject
  public DiceRollerViewModel() {
    super();
  }
}
