package com.chrisjaunes.communication.client.contacts.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.myView.ChatTextStyleView;
import com.chrisjaunes.communication.client.utils.BitmapHelper;
/**
 * 设计模式：适配器模式（对象适配器）
 * version 1.1 : 使用了适配器模式
 * 持有 ContactsRaw，处于编码简便的考虑，没有单独出一个接口
 * version 1.2 : CONTACTS_VIEW_DEFAULT
 * @author chrisjuanes
 * @version 1.2
 */
public class ContactsView{
    public static final ContactsView CONTACTS_VIEW_DEFAULT = new ContactsView(new ContactsRaw());

    private ContactsRaw contactsRaw;
    private Bitmap avatarView;
    private ChatTextStyleView chatTextStyleView;
    public ContactsView(@NonNull final ContactsRaw contactsRaw) {
        setContactsRaw(contactsRaw);
    }

    public void setContactsRaw(@NonNull final ContactsRaw contactsRaw) {
        this.contactsRaw = contactsRaw;
        if (null == contactsRaw.getAvatar()) {
            avatarView = BitmapHelper.AVATAR_DEFAULT;
        } else {
            avatarView = BitmapHelper.StringToBitmap(contactsRaw.getAvatar());
        }
        if (null == contactsRaw.getTextStyle()) {
            chatTextStyleView = ChatTextStyleView.CHAT_TEXT_STYLE_DEFAULT;
        } else {
            chatTextStyleView = ChatTextStyleView.valueOf(contactsRaw.getTextStyle());
        }
    }
    public ContactsRaw getContactsRaw() {return contactsRaw;}

    public String getAccount() {
        return contactsRaw.getAccount();
    }
    public String getNickName() {
        return contactsRaw.getNickname();
    }
    public Bitmap getAvatarView() {
        return avatarView;
    }
    public ChatTextStyleView getChatTextStyleView() {
        return chatTextStyleView;
    }
}
