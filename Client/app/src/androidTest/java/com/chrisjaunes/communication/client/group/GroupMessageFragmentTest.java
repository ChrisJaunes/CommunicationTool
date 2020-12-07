package com.chrisjaunes.communication.client.group;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.LoginActivity;
import com.chrisjaunes.communication.client.contacts.RecycleViewMatcher;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GroupMessageFragmentTest {
    @Test
    public void Test1() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.radio_button_chatgroup)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(1000);
    }
    @Test
    public void Test2() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("444"));
        onView(withId(R.id.et_password)).perform(typeText("444"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.radio_button_chatgroup)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(1000);
    }
    @Test
    public void Test3() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.radio_button_chatgroup)).perform(click());
        Thread.sleep(1000);
        onView(new RecycleViewMatcher(R.id.rv_group).atPosition(1)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
        onView(withId(R.id.tv_send_text)).perform(typeText("UI espresso test"));
        onView(withId(R.id.btn_send_text)).perform(click());
        Thread.sleep(100);
    }
    @Test
    public void Test4() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("444"));
        onView(withId(R.id.et_password)).perform(typeText("444"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.radio_button_chatgroup)).perform(click());
        Thread.sleep(100);
        openContextualActionModeOverflowMenu();
        Thread.sleep(300);
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(2).perform(click());
        Thread.sleep(3000);
    }
    @Test
    public void Test5() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("444"));
        onView(withId(R.id.et_password)).perform(typeText("444"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.radio_button_chatgroup)).perform(click());
        Thread.sleep(1000);
        onView(new RecycleViewMatcher(R.id.rv_group).atPosition(0)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(100);
    }

}