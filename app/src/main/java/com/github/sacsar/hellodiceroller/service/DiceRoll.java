package com.github.sacsar.hellodiceroller.service;

import java.util.List;

public class DiceRoll {
  private final List<Integer> rolls;

  public DiceRoll(List<Integer> rolls) {
    this.rolls = rolls;
  }

  public List<Integer> getRolls() {
    return rolls;
  }
}
