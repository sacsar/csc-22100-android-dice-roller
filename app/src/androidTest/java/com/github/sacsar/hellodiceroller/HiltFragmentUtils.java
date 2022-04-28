package com.github.sacsar.hellodiceroller;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

public class HiltFragmentUtils {
  // See the standard launchFragmentInContainer won't work with Hilt. Adapt the architecture sample
  // to launch the fragment in the HiltTestActivity (which is empty)
  // https://github.com/android/architecture-samples/blob/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/HiltExt.kt#L38
  public static <T extends Fragment> void launchHiltFragmentInContainer(
      ActivityScenarioRule<HiltTestActivity> activityScenarioRule, Class<T> klass) {

    activityScenarioRule
        .getScenario()
        .onActivity(
            activity -> {
              Fragment fragment =
                  activity
                      .getSupportFragmentManager()
                      .getFragmentFactory()
                      .instantiate(klass.getClassLoader(), klass.getName());
              activity
                  .getSupportFragmentManager()
                  .beginTransaction()
                  .add(android.R.id.content, fragment, "")
                  .commitNow();
            });
  }
}
