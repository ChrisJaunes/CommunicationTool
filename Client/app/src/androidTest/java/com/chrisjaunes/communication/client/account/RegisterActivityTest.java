package com.chrisjaunes.communication.client.account;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import com.chrisjaunes.communication.client.R;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class RegisterActivityTest {
    @Test
    public void onCreateTest1() throws InterruptedException {
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("hjj"));
        onView(withId(R.id.et_password)).perform(typeText("hjj"));
        onView(withId(R.id.et_nickname)).perform(typeText("hjj"));
        onView(withId(R.id.sp_font_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());
        onView(withId(R.id.sp_bubble_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(4).perform(click());
        onView(withId(R.id.sp_border_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onView(withId(R.id.btn_register)).perform(click());
        Thread.sleep(1000);
        assert scenario.getState() == Lifecycle.State.DESTROYED;
    }
    @Test
    public void onCreateTest2() throws InterruptedException {
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("hjj"));
        onView(withId(R.id.et_password)).perform(typeText("hjj"));
        onView(withId(R.id.et_nickname)).perform(typeText("hjj"));
        onView(withId(R.id.sp_font_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());
        onView(withId(R.id.sp_bubble_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(4).perform(click());
        onView(withId(R.id.sp_border_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onView(withId(R.id.btn_register)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void onCreateTest3() throws InterruptedException {
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
        Thread.sleep(1000);
        scenario.close();
    }
}