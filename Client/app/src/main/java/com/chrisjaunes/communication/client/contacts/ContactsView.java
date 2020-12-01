package com.chrisjaunes.communication.client.contacts;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.account.ChatTextStyle;
import com.chrisjaunes.communication.client.myView.ChatTextView;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

public class ContactsView{
    private final Contacts contacts;
    private final Bitmap avatar;
    private final ChatTextStyle chatTextStyle;
    public ContactsView(Contacts contacts) {
        this.contacts = contacts;
        Log.d("ContactsView", "" + contacts + contacts.getAvatar());//contacts.getAvatar());
        avatar = BitmapHelper.StringToBitmap(contacts.getAvatar());
        chatTextStyle = ChatTextStyle.valueOf(contacts.getTextStyle());
    }

    public static ContactsView ContactsViewDefault() {
        return new ContactsView(new Contacts());
    }

    public String getAccount() {
        return contacts.getAccount();
    }
//    public LiveData<String> getAccountNickNameLiveData() {}
    public String getNickName() {
        return contacts.getNickname();
    }
    public Bitmap getAvatar() {
        return avatar;
    }
    public ChatTextStyle getChatTextStyle() { return chatTextStyle; }
}
