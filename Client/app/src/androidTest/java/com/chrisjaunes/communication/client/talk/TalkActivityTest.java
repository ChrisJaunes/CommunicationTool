package com.chrisjaunes.communication.client.talk;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.LoginActivity;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TalkActivityTest {
    @Test
    public void Test1() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
    }
    @Test
    public void Test2() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.tv_send_text)).perform(typeText("UI espresso test"));
        onView(withId(R.id.btn_send_text)).perform(click());
        Thread.sleep(1000);
    }
    @Test
    public void Test3() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("333"));
        onView(withId(R.id.et_password)).perform(typeText("333"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.tv_send_text)).perform(typeText("UI espresso test 2"));
        onView(withId(R.id.btn_send_text)).perform(click());
        Thread.sleep(1000);
    }
    @Test
    public void Test4() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.tv_send_text)).perform(typeText("UI espresso test"));
        onView(withId(R.id.btn_send_text)).perform(click());
        Thread.sleep(1000);
    }

}