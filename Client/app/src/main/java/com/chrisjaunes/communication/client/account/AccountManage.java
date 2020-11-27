package com.chrisjaunes.communication.client.account;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.chrisjaunes.communication.client.utils.BitmapHelper;

public class AccountManage {
    private static AccountManage instance;
    private AccountManage (){}
    public static AccountManage getInstance() {
        if (null == instance) instance = new AccountManage();
        return instance;
    }

    private Account account;
    private final MutableLiveData<Bitmap> avatarLiveData = new MutableLiveData<>();
    public void setAccount(Account account) {
        this.account = account;
        avatarLiveData.postValue(BitmapHelper.StringToBitmap(account.avatar));
    }
    public Account getAccount() {
        return account;
    }
    public void setAvatar(final Bitmap avatar) {
        avatarLiveData.postValue(avatar);
    }
}
