package com.github.sacsar.hellodiceroller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import org.junit.Rule;
import org.junit.Test;

@HiltAndroidTest
public class DiceRollerTest {
  // From the Dagger/Hilt docs: If your test uses multiple test rules, make sure that the
  // HiltAndroidRule
  // runs before any other test rules that require access to the Hilt component
  @Rule(order = 0)
  public HiltAndroidRule hiltAndroidRule = new HiltAndroidRule(this);

  @Rule(order = 1)
  public ActivityScenarioRule<HiltTestActivity> activityScenarioRule =
      new ActivityScenarioRule<>(HiltTestActivity.class);

  @Test
  public void testDiceRollerButtonText() {
    HiltFragmentUtils.launchHiltFragmentInContainer(activityScenarioRule, DiceRoller.class);
    onView(withId(R.id.rollButton)).check(matches(withText("Roll")));
  }
}
