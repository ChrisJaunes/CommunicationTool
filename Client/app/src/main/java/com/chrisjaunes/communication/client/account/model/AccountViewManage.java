package com.chrisjaunes.communication.client.account.model;

/**
 * @author ChrisJaunes
 * @version 1
 * @status XXX
 * 设计模式：单例模式(懒汉模式)
 * 为了保证在多个线程中使用这个单例类，对getInstance加锁
 */
public class AccountViewManage {
    private static AccountViewManage instance;
    private AccountViewManage() {}
    public static synchronized AccountViewManage getInstance() {
        if (null == instance) instance = new AccountViewManage();
        return instance;
    }

    private AccountView accountView;
    public void setAccount(AccountRaw accountRaw) {
        this.accountView = new AccountView(accountRaw);
    }
    public AccountView getAccountView() {
        assert null != accountView;
        return accountView;
    }
}
