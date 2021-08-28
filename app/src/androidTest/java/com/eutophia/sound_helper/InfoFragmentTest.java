package com.eutophia.sound_helper;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

import android.view.View;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class InfoFragmentTest extends TestCase {
    private String stringToBetyped;

    @Rule
    public ActivityTestRule<InfoActivity> activityRule = new ActivityTestRule<InfoActivity>(
            InfoActivity.class);

    ////////////////////////////////////////
    // test for InfoFragment
    ////////////////////////////////////////

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

    @Test
    public void testDatePicker() {
        Espresso.onView(ViewMatchers.withId(R.id.birth_button))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2021, 8, 27));
    }

    ////////////////////////////////////////
    // test for DiseaseFragment
    ////////////////////////////////////////

    @Before
    public void init() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
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
    public void testDiseaseElementDisplayed() throws Exception {
        onView(withId(R.id.select_disease_textView))
                .check(matches(isDisplayed()));
        onView(withId(R.id.select_disease_spinner))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testDiseaseSelectBySpinnerText() throws Exception {
        final int selectDiseaseId = R.id.select_disease_spinner;
        String[] diseaseList = {
                "Diabetes", "Asthma", "Dementia", "Visual impairment", "Hearing impairment", "None", "Other"
        };
        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        for (int i = 0; i < diseaseList.length; i++) {
            String disease = diseaseList[i];
            onView(withId(selectDiseaseId))
                    .perform(click());
            onData(allOf(is(instanceOf(String.class)), is(disease)))
                    .perform(click());

            UiObject button = uiDevice.findObject(new UiSelector().text("ENTER"));
            if (button.exists() && button.isEnabled()) {
                button.click();
            }

            onView(withId(selectDiseaseId))
                    .check(matches(withSpinnerText(containsString(disease))));
        }
    }
}