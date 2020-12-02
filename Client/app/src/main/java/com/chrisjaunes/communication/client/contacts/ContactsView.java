package com.chrisjaunes.communication.client.contacts;

import android.graphics.Bitmap;
import android.util.Log;

import com.chrisjaunes.communication.client.account.model.ChatTextStyle;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

public class ContactsView{
    private final ContactsRaw contactsRaw;
    private final Bitmap avatar;
    private final ChatTextStyle chatTextStyle;
    public ContactsView(ContactsRaw contactsRaw) {
        this.contactsRaw = contactsRaw;
        Log.d("ContactsView", "" + contactsRaw + contactsRaw.getAvatar());//contacts.getAvatar());
        avatar = BitmapHelper.StringToBitmap(contactsRaw.getAvatar());
        chatTextStyle = ChatTextStyle.valueOf(contactsRaw.getTextStyle());
    }

    public static ContactsView ContactsViewDefault() {
        return new ContactsView(new ContactsRaw());
    }

    public String getAccount() {
        return contactsRaw.getAccount();
    }
//    public LiveData<String> getAccountNickNameLiveData() {}
    public String getNickName() {
        return contactsRaw.getNickname();
    }
    public Bitmap getAvatar() {
        return avatar;
    }
    public ChatTextStyle getChatTextStyle() { return chatTextStyle; }
}
