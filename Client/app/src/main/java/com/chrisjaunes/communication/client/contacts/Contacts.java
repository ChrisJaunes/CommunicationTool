package com.chrisjaunes.communication.client.contacts;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.chrisjaunes.communication.client.Config;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Contacts {
    @NonNull
    @PrimaryKey
    private String account;
    private String nickname;
    private String avatar;
    private String textStyle;
    private String time;

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getTextStyle() {
        return textStyle;
    }


    @Ignore
    public static Contacts jsonToContacts(JSONObject jsonO) {
        Contacts contacts = new Contacts();
        try {
            if (jsonO.has(Config.STR_ACCOUNT))    contacts.account = jsonO.getString(Config.STR_ACCOUNT);
            if (jsonO.has(Config.STR_NICKNAME))   contacts.nickname = jsonO.getString(Config.STR_NICKNAME);
            if (jsonO.has(Config.STR_AVATAR))     contacts.avatar = jsonO.getString(Config.STR_AVATAR);
            if (jsonO.has(Config.STR_TEXT_STYLE)) contacts.textStyle = jsonO.getString(Config.STR_TEXT_STYLE);
            if (jsonO.has(Config.STR_TIME))       contacts.time = jsonO.getString(Config.STR_TIME);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
