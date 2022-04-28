package com.github.sacsar.hellodiceroller;

import android.app.Application;
import android.content.Context;
import androidx.test.runner.AndroidJUnitRunner;
import dagger.hilt.android.testing.HiltTestApplication;

/** See https://developer.android.com/training/dependency-injection/hilt-testing#java */
public class HiltTestRunner extends AndroidJUnitRunner {
  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    return super.newApplication(cl, HiltTestApplication.class.getName(), context);
  }
}
