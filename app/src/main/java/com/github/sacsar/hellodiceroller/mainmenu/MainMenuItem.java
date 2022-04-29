package com.github.sacsar.hellodiceroller.mainmenu;

import androidx.navigation.NavDirections;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MainMenuItem {
  public abstract NavDirections menuToTargetAction();

  public abstract String displayString();

  public static MainMenuItem create(NavDirections menuToTargetAction, String displayName) {
    return new AutoValue_MainMenuItem(menuToTargetAction, displayName);
  }
}
