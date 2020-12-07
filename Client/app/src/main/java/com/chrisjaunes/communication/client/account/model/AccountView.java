package com.chrisjaunes.communication.client.account.model;

import android.graphics.Bitmap;

import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.myView.ChatTextStyleView;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

/**
 * @author chrisjuanes
 * @version 1
 * @status XXX
 * 设计模式：适配器模式（对象适配器）
 * 功能：对AccountRaw进行了封装，以便于外部使用
 * 持有 AccountRaw，处于编码简便的考虑，没有单独出一个接口
 *
 */
public class AccountView {
    final AccountRaw accountRaw;
    Bitmap avatarView;
    ChatTextStyleView chatTextStyleView;
    public AccountView(final AccountRaw accountRaw) {
        assert null != accountRaw && null != accountRaw.nickname;
        this.accountRaw = accountRaw;
        if (null == accountRaw.avatar) {
            avatarView = BitmapHelper.AVATAR_DEFAULT;
        } else {
            avatarView = BitmapHelper.StringToBitmap(accountRaw.avatar);
        }
        if (null == accountRaw.text_style) {
            chatTextStyleView = ChatTextStyleView.CHAT_TEXT_STYLE_DEFAULT;
        } else {
            chatTextStyleView = ChatTextStyleView.valueOf(accountRaw.text_style);
        }
    }
    public String getAccount() {
        return accountRaw.account;
    }
    public String getNickName() {
        return accountRaw.nickname;
    }
    public void setAvatarView(final Bitmap bitmap) {
        this.avatarView = bitmap;
    }
    public Bitmap getAvatarView() {
        return avatarView;
    }
    public ChatTextStyleView getChatTextStyleView() {
        return chatTextStyleView;
    }
}
