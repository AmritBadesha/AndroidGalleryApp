package com.example.android_gallery_app;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static org.hamcrest.core.StringContains.containsString;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("table"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.reset)).perform(click());
        onView(withId(R.id.btnSearch)).perform(click());
        //location search show all results
        onView(withId(R.id.topLeft)).perform(typeText("35, -125"), closeSoftKeyboard());
        onView(withId(R.id.BottomRight)).perform(typeText("38, -120"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.reset)).perform(click());
        // location search show no results
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.topLeft)).perform(typeText("0, 0"), closeSoftKeyboard());
        onView(withId(R.id.BottomRight)).perform(typeText("20, -130"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.reset)).perform(click());
        // time search

        onView(withId(R.id.etFromDateTime)).perform(replaceText("2020-01-01"));



    }
}
