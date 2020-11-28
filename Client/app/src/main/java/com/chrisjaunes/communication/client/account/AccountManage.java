package com.chrisjaunes.communication.client.account;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.contacts.Contacts;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

/**
 * Account.account 不允许设置
 */
public class AccountManage {
    private static AccountManage instance;
    private AccountManage (){}
    public static AccountManage getInstance() {
        if (null == instance) instance = new AccountManage();
        return instance;
    }

    private AccountInfo accountInfo;
    private final MutableLiveData<Bitmap> avatarLiveData = new MutableLiveData<>();
    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        avatarLiveData.postValue(BitmapHelper.StringToBitmap(accountInfo.avatar));
    }
    public AccountInfo getAccountInfo() {
        return accountInfo;
    }
    public String getAccount() { return null == accountInfo.account ? Config.ACCOUNT_VISITORS : accountInfo.account;}
    public void setAvatar(final Bitmap avatar) {
        avatarLiveData.postValue(avatar);
    }
}
