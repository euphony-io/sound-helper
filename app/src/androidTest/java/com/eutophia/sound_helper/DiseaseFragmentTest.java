package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// The @Runwith annotation will tell the JUnit to run the tests in this class using the Android JUnit test runner.
@RunWith(AndroidJUnit4.class)
public class DiseaseFragmentTest {
    // start InfoActivity
    @Rule
    public ActivityTestRule<InfoActivity> activityDiseaseFragmentTest = new ActivityTestRule<InfoActivity>(InfoActivity.class);

    @Before
    public void init() {
        activityDiseaseFragmentTest.getActivity().getSupportFragmentManager().beginTransaction();
    }

    public static ViewAction waitFor(long delay) {
        // onView(isRoot()).perform(waitFor(1000));
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }

    @Test
    public void selectBySpinnerText() throws Exception {
        onView(withId(R.id.select_disease))
                .perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Diabetes")))
                .perform(click());
    }
}