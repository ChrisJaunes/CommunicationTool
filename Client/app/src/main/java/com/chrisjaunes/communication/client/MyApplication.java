package com.chrisjaunes.communication.client;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.chrisjaunes.communication.client.account.LoginActivity;
import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.account.AccountViewManage;

public class MyApplication extends Application {
    private static MyApplication instance = null;
    public static MyApplication getInstance(){
        return instance;
    }
    public MyApplication() {
        instance = this;
    }

    public void setAccount(AccountRaw account) {
        AccountViewManage.getInstance().setAccount(account);
    }

    LocalDatabase localDatabase;
    public LocalDatabase getLocalDataBase() {
        if(null == localDatabase) {
            localDatabase = Room.databaseBuilder(this, LocalDatabase.class,
                    String.format("account_%s", AccountViewManage.getInstance().getAccountView().getAccount())
            )
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return localDatabase;
    }


    public void logout() {
        localDatabase = null;
        SharedPreferences.Editor editor = getSharedPreferences("login_account", Context.MODE_PRIVATE).edit();
        editor.putString(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, "");
        editor.putString(LoginActivity.STR_AUTO_LOGIN_PASSWORD, "");
        editor.putBoolean(LoginActivity.STR_AUTO_LOGIN, false);
        editor.apply();
    }

    public void setLoginAuto(final String account, final String password) {
        SharedPreferences.Editor editor = getSharedPreferences("login_account", Context.MODE_PRIVATE).edit();
        editor.putString(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, account);
        editor.putString(LoginActivity.STR_AUTO_LOGIN_PASSWORD, password);
        editor.putBoolean(LoginActivity.STR_AUTO_LOGIN, true);
        editor.apply();
    }
}

