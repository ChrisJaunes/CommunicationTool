package com.chrisjaunes.communication.client.account;

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

public class RegisterActivityTest {
    @Test
    public void onCreateTest1() {
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("aaa"));
        onView(withId(R.id.et_password)).perform(typeText("aaa"));
        onView(withId(R.id.et_nickname)).perform(typeText("aaa"));
        onView(withId(R.id.sp_font_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());
        onView(withId(R.id.sp_bubble_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(4).perform(click());
        onView(withId(R.id.sp_border_color)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.btn_register)).check(doesNotExist());
        scenario.close();
    }

    @Test
    public void onCreateTest2() {
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void onCreateTest3() {
//        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
//        onView(withId(R.id.iv_avatar)).perform(click());
//        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
//        scenario.close();
//        openContextualActionModeOverflowMenu();
    }

}