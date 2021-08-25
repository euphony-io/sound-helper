package com.eutophia.sound_helper;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.junit.Assert.assertEquals;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TransmitActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            TransmitActivity.class);

    @Test
    public void testButtonDisplay(){
        onView(withId(R.id.transmit_button))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testTransmitMsg() {
        onView(withId(R.id.transmit_infoTV))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testStopTransmit() {
        onView(withId(R.id.transmit_infoTV))
                .check(matches(isDisplayed()));

        onView(withId(R.id.transmit_button))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }
}