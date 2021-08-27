package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DateFragmentTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            InfoActivity.class);

    @Test
    public void onViewCreated() {
        onView(withId(R.id.birth_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}