package com.chrisjaunes.communication.client.account;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.account.model.AccountView;

/**
 * 设计模式：单例模式(懒汉模式)
 * version 1.1 为了保证在多个线程中使用这个单例类，对getInstance加锁
 * @author ChrisJaunes
 * @version 1.1
 */
public class AccountViewManage {
    private static AccountViewManage instance;
    private AccountViewManage() {}
    public static synchronized AccountViewManage getInstance() {
        if (null == instance) instance = new AccountViewManage();
        return instance;
    }

    private AccountView accountView;
    public void setAccount(@NonNull final AccountRaw accountRaw) {
        this.accountView = new AccountView(accountRaw);
    }
    public AccountView getAccountView() {
        assert null != accountView;
        return accountView;
    }
}
