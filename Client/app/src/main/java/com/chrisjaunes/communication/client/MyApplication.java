package com.chrisjaunes.communication.client;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chrisjaunes.communication.client.account.Account;
import com.chrisjaunes.communication.client.account.AccountManage;

public class MyApplication extends Application {
    private static MyApplication instance = null;
    public static MyApplication getInstance(){
        return instance;
    }
    public MyApplication() {
        instance = this;

    }

    public void setAccount(Account account) {
        AccountManage.getInstance().setAccount(account);
    }
}

