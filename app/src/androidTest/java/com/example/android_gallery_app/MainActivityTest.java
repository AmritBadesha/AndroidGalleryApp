package com.example.android_gallery_app;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());

        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("table"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.etCaption)).check(matches(withText("my table and dog")));
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.etCaption)).check(matches(withText("tv and table")));
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.reset)).perform(click());

        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(typeText(" 18:00:00"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.etCaption)).check(matches(withText("new photo 6pm")));
        onView(withId(R.id.reset)).perform(click());

        //location search show all results
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
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
        onView(withId(R.id.etFromDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(typeText(" 00:00:00"), closeSoftKeyboard());
        onView(withId(R.id.topLeft)).perform(typeText("0, 0"), closeSoftKeyboard());
        onView(withId(R.id.BottomRight)).perform(typeText("20, -130"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.reset)).perform(click());

    }

}
