package com.chrisjaunes.communication.client;

import android.app.Application;

import com.chrisjaunes.communication.client.account.Account;

public class MyApplication extends Application {
    private static MyApplication instance = null;
    public static MyApplication getInstance(){
        return instance;
    }
    public MyApplication() {
        instance = this;

    }

    private Account account;
    public void setAccount(Account account) {
        this.account = account;
    }
    public Account getAccount() {
        return account;
    }
}

