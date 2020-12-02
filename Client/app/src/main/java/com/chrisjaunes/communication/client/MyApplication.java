package com.chrisjaunes.communication.client;

import android.app.Application;

import androidx.room.Room;

import com.chrisjaunes.communication.client.account.AccountInfo;
import com.chrisjaunes.communication.client.account.AccountViewManage;

public class MyApplication extends Application {
    private static MyApplication instance = null;
    public static MyApplication getInstance(){
        return instance;
    }
    public MyApplication() {
        instance = this;

    }

    public void setAccount(AccountInfo account) {
        AccountViewManage.getInstance().setAccountInfo(account);
    }

    LocalDatabase localDatabase;
    public LocalDatabase getLocalDataBase() {
        if(null == localDatabase) {
            localDatabase = Room.databaseBuilder(this, LocalDatabase.class,
                    String.format("account_%s", AccountViewManage.getInstance().getAccount())
            )
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return localDatabase;
    }
}

