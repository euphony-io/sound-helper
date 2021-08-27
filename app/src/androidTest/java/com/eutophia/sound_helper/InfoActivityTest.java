package com.eutophia.sound_helper;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InfoActivityTest extends TestCase {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            InfoActivity.class);

    @Test
    public void testTextDisplay(){
        Espresso.onView(ViewMatchers.withId(R.id.enter_info_textView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testFragmentDisplay(){
        Espresso.onView(ViewMatchers.withId(R.id.info_fragment))
                .check(matches(isDisplayed()));
    }
}