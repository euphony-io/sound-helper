package com.eutophia.sound_helper;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
    private String stringToBetyped;
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            InfoActivity.class);

    @Test
    public void testConfirmButtonClick() {
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
    public void testConfirmDialogPositiveButtonClick() {
        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("confirm"))
                .perform(click());
    }

    @Test
    public void testName() {
        // press the edit button and then type text
        onView(withId(R.id.name_editText)).perform(click(), typeText("Euphony"));
    }

    @Test
    public void testContactNum() {
        onView(withId(R.id.tel_editText)).perform(click(), typeText("01000000000"));
    }
}