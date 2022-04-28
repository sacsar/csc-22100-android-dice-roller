package com.github.sacsar.hellodiceroller;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class DiceRollerApplication extends Application {
  // In order to use Hilt, we need to have an application class, but we don't
  // need it to do anything at the moment.
}
