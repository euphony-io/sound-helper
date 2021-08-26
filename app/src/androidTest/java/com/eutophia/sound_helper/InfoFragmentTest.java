package com.eutophia.sound_helper;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InfoFragmentTest extends TestCase {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            InfoActivity.class);

    @Test
    public void testConfirmButtonClick(){
        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
    }
    @Test
    public void testConfirmDialogDisplay() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testConfirmDialogPositiveButtonClick(){
        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("confirm"))
                .perform(click());
    }
}