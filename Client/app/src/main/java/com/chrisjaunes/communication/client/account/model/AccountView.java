package com.chrisjaunes.communication.client.account.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.my_view.ChatTextStyleView;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

/**
 * 设计模式：适配器模式（对象适配器）
 * version 1.1 : 使用了适配器模式
 * 功能：对AccountRaw进行了封装，以便于外部使用
 * 持有 AccountRaw，处于编码简便的考虑，没有单独出一个接口
 * version 1.2 : 增加了ACCOUNT_VIEW_DEFAULT
 * @author chrisjuanes
 * @version 1.2
 */
public class AccountView {
    static public final AccountView ACCOUNT_VIEW_DEFAULT = new AccountView(new AccountRaw.Builder().build());

    private final AccountRaw accountRaw;
    private Bitmap avatarView;
    private ChatTextStyleView chatTextStyleView;
    public AccountView(@NonNull final AccountRaw accountRaw) {
        assert null != accountRaw.nickname;
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
    // HINT only be used by AccountViewManage
    public void setAvatarView(@NonNull final Bitmap bitmap) {
        this.avatarView = bitmap;
    }
    public Bitmap getAvatarView() {
        return avatarView;
    }
    public void setChatTextStyleView(@NonNull final ChatTextStyleView chatTextStyleView) {
        this.chatTextStyleView = chatTextStyleView;
    }
    public ChatTextStyleView getChatTextStyleView() { return chatTextStyleView; }
}
