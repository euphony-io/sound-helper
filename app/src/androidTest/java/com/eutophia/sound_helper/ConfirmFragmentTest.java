package com.eutophia.sound_helper;

import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ConfirmFragmentTest extends TestCase {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            InfoActivity.class);

    @Test
    public void testButtonClick(){
        runFragment();

        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
    }
    @Test
    public void testDialogDisplay() throws InterruptedException {
        runFragment();

        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testDialogPositiveButtonClick(){
        runFragment();

        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("confirm"))
                .perform(click());
    }
    @Test
    public void testDialogNegativeButtonClick(){
        runFragment();

        Espresso.onView(ViewMatchers.withId(R.id.confirm_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withText(R.string.submit))
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("cancel"))
                .perform(click());
    }
    public void runFragment(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConfirmFragment fragment = startFragment();

            }
        });
    }
    private ConfirmFragment startFragment() {
        InfoActivity activity = (InfoActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        ConfirmFragment fragment = new ConfirmFragment();
        transaction.add(fragment, "Confirm");
        transaction.commit();
        return fragment;
    }
}