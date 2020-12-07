package com.chrisjaunes.communication.client.contacts;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.LoginActivity;

import org.junit.Before;
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

public class ContactsFragmentTest {
    @Before
    public void after() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("111"));
        onView(withId(R.id.et_password)).perform(typeText("111"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
    }
    @Test
    public void Test1() {
        onView(withId(R.id.rb_now_contacts)).perform(click());
    }
    @Test
    public void Test2() {
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
    }
    @Test
    public void Test3() {
        onView(withId(R.id.rb_now_contacts)).perform(click());
        onView(withId(R.id.layout_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
    @Test
    public void Test4() throws InterruptedException {
        openContextualActionModeOverflowMenu();
        Thread.sleep(300);
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(0).perform(click());
    }
    @Test
    public void Test5() throws InterruptedException {
        openContextualActionModeOverflowMenu();
        Thread.sleep(300);
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(1).perform(click());
    }
}