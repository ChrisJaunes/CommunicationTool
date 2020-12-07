package com.chrisjaunes.communication.client.contacts;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.LoginActivity;

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

public class ContactsFragmentTest2 {
    @Test
    public void Test1() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        openContextualActionModeOverflowMenu();
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(1).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.et_search_account)).perform(typeText("eee"));
        onView(withId(R.id.btn_search_account)).perform(click());
    }
    @Test
    public void Test2() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("eee"));
        onView(withId(R.id.et_password)).perform(typeText("eee"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        openContextualActionModeOverflowMenu();
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(0).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());

        Thread.sleep(1000);
        onView(new RecycleViewMatcher(R.id.rv_contacts).atPositionOnView(0, R.id.btn_agree)).perform(click());

        Thread.sleep(1000);
        onView(new RecycleViewMatcher(R.id.rv_contacts).atPositionOnView(0, R.id.btn_agree)).perform(click());
    }

    @Test
    public void Test3() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(1000);
    }
    @Test
    public void Test4() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("aaa"));
        onView(withId(R.id.et_password)).perform(typeText("aaa"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        Thread.sleep(1000);
    }
}