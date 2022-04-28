package com.github.sacsar.hellodiceroller.service;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A service that rolls the dice. While this is just a random number generator, it's playing the
 * role of some sort of external call, whether to an API or a database.
 */
@Singleton
public class DiceRollerService {

  private static final Random RANDOM = new Random();

  public Single<DiceRoll> rollDice(int numDice, int numSides) {
    // Even though we're doing essentially no work here, make sure we don't perform it on the UI
    // thread.
    return Single.fromCallable(
            () -> {
              List<Integer> rolls =
                  RANDOM.ints(numDice, 1, numSides + 1).boxed().collect(Collectors.toList());
              return new DiceRoll(rolls);
            })
        .subscribeOn(Schedulers.computation());
  }

  /**
   * Constructor for the {@link DiceRollerService}. It needs to be annotated with @Inject so Hilt
   * can find it.
   */
  @Inject
  public DiceRollerService() {}
}
