package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testInformButtonClick() {
        onView(withId(R.id.enter_inform_button)).perform(click());
    }

    @Test
    public void testListenerButtonClick() {
        onView(withId(R.id.enter_listener_button)).perform(click());
    }
}