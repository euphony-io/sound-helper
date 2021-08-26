package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListenerActivityTest {
    @Rule
    public ActivityTestRule<ListenerActivity> mActivityRule = new ActivityTestRule(ListenerActivity.class);

    @Test
    public void testButtonClick() {
        onView(withId(R.id.listen_button))
                .perform(click())
                .check(matches(withText("Stop")));
    }
}