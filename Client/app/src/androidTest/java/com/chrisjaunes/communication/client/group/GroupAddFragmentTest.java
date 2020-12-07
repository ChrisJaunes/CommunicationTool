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

public class GroupAddFragmentTest {
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
        onView(withId(R.id.et_account)).perform(typeText("444"));
        onView(withId(R.id.et_password)).perform(typeText("444"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(100);
        openContextualActionModeOverflowMenu();
        Thread.sleep(100);
        onData(allOf(is(instanceOf(androidx.appcompat.view.menu.MenuItemImpl.class)))).atPosition(2).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.create_group_name)).perform(typeText("UI group test"));
        onView(new RecycleViewMatcher(R.id.create_group_person_list).atPositionOnView(0, R.id.create_group_checkbox)).perform(click());
        onView(new RecycleViewMatcher(R.id.create_group_person_list).atPositionOnView(1, R.id.create_group_checkbox)).perform(click());
        onView(withId(R.id.create_group_confirm_button)).perform(click());
    }
    @Test
    public void Test4() throws InterruptedException {
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
    public void Test5() throws InterruptedException {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_account)).perform(typeText("555"));
        onView(withId(R.id.et_password)).perform(typeText("555"));
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

}