package com.chrisjaunes.communication.client.contacts.model;

import android.graphics.Bitmap;

import com.chrisjaunes.communication.client.myView.ChatTextStyleView;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

public class ContactsView{
    public static final ContactsView CONTACTS_DEFAULT = new ContactsView(new ContactsRaw());

    private ContactsRaw contactsRaw;
    private Bitmap avatarView;
    private ChatTextStyleView chatTextStyleView;
    public ContactsView(final ContactsRaw contactsRaw) {
        setContactsRaw(contactsRaw);
    }

    public void setContactsRaw(final ContactsRaw contactsRaw) {
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
