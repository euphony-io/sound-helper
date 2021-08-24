package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InfoFragmentTest {
    private String stringToBetyped;

    @Rule
    public ActivityTestRule<InfoActivity> InfoActivityTestRule = new ActivityTestRule(InfoActivity.class);

    @Test
    public void testName() {
        // press the edit button and then type text
        onView(withId(R.id.editName)).perform(click(),typeText("Euphony"));
    }

    @Test
    public void testContactNum() {
        onView(withId(R.id.editTel)).perform(click(),typeText("01000000000"));
    }
}