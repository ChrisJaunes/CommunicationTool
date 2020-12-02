package com.chrisjaunes.communication.client.account;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

/**
 * Account.account 不允许设置
 */
public class AccountViewManage {
    private static AccountViewManage instance;
    private AccountViewManage(){}
    public static AccountViewManage getInstance() {
        if (null == instance) instance = new AccountViewManage();
        return instance;
    }

    private AccountRaw accountRaw;
    private final MutableLiveData<Bitmap> avatarLiveData = new MutableLiveData<>();
    public void setAccountRaw(AccountRaw accountRaw) {
        this.accountRaw = accountRaw;
        avatarLiveData.postValue(BitmapHelper.StringToBitmap(accountRaw.avatar));
    }
    public AccountRaw getAccountRaw() {
        return accountRaw;
    }
    public String getAccount() { return null == accountRaw.account ? Config.ACCOUNT_VISITORS : accountRaw.account;}
    public void setAvatar(final Bitmap avatar) {
        avatarLiveData.postValue(avatar);
    }

    public AccountView getAccountView() {return new AccountView();}
}
