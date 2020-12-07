package com.chrisjaunes.communication.client.account;

import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.chrisjaunes.communication.client.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Test
    public void onCreateTest1() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("not correct password"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void onCreateTest2() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.btn_login)).check(doesNotExist());
        scenario.close();
    }
    @Test
    public void onCreateTest3() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.btn_login)).check(doesNotExist());
        scenario.close();
    }
    @Test
    public void onCreateTest4() {
        Bundle bundle = new Bundle();
        bundle.putString(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, "111");
        bundle.putString(LoginActivity.STR_AUTO_LOGIN_PASSWORD, "111");
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class, bundle);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void onCreateTest5() {
        Bundle bundle = new Bundle();
        bundle.putString(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, "111");
        bundle.putString(LoginActivity.STR_AUTO_LOGIN_PASSWORD, "111");
        bundle.putBoolean(LoginActivity.STR_AUTO_LOGIN, true);
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class, bundle);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        scenario.close();
    }
}