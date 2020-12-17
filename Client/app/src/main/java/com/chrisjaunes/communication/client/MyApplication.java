package com.chrisjaunes.communication.client;

import android.app.Application;

import androidx.room.Room;

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
        //setAccount(AccountRaw.ACCOUNT_RAW_DEFAULT);
        localDatabase = null;
    }
}

