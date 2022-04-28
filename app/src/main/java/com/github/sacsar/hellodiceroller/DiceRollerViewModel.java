package com.github.sacsar.hellodiceroller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.sacsar.hellodiceroller.service.DiceRollerService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;

@HiltViewModel
public class DiceRollerViewModel extends ViewModel {
  @Inject DiceRollerService diceRollerService;

  CompositeDisposable compositeDisposable = new CompositeDisposable();

  MutableLiveData<Integer> roll = new MutableLiveData<>();

  // Hilt view models need an Inject-annotated constructor.
  @Inject
  public DiceRollerViewModel() {
    super();
  }

  public LiveData<Integer> roll() {
    return roll;
  }

  public void rollDice() {
    // TODO: Eventually we'll support multiple dice and different numbers of sides, but not now.
    compositeDisposable.add(
        diceRollerService
            .rollDice(1, 6)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(diceRoll -> roll.setValue(diceRoll.getRolls().get(0))));
  }

  public void cleanup() {
    compositeDisposable.dispose();
  }
}
