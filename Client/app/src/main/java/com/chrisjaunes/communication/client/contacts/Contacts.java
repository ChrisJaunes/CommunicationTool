package com.chrisjaunes.communication.client.contacts;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.chrisjaunes.communication.client.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

@Entity
public class Contacts {
    @NonNull
    @PrimaryKey
    private String account;
    private String nickname;
    private String avatar;
    private String textStyle;
    private String time;
    private int operation = 0;

    public Contacts() {
        account = Config.ACCOUNT_VISITORS;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNickname() { return nickname; }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAvatar() { return avatar; }

    public void setTextStyle(String textStyle) { this.textStyle = textStyle; }

    public String getTextStyle() { return textStyle; }

    public void setTime(String time) { this.time = time; }

    public String getTime() { return time; }

    public void setOperation(int operation) { this.operation = operation; }

    public int getOperation() { return operation; }

    @NonNull
    @Ignore
    public static Contacts jsonToContacts(@NonNull JSONObject jsonO) {
        Contacts contacts = new Contacts();
        try {
            if (jsonO.has(Config.STR_ACCOUNT))    contacts.account = jsonO.getString(Config.STR_ACCOUNT);
            if (jsonO.has(Config.STR_NICKNAME))   contacts.nickname = jsonO.getString(Config.STR_NICKNAME);
            if (jsonO.has(Config.STR_AVATAR))     contacts.avatar = jsonO.getString(Config.STR_AVATAR);
            if (jsonO.has(Config.STR_TEXT_STYLE)) contacts.textStyle = jsonO.getString(Config.STR_TEXT_STYLE);
            if (jsonO.has(Config.STR_TIME))       contacts.time = jsonO.getString(Config.STR_TIME);
            if (jsonO.has(Config.STR_OPERATION))  contacts.operation = jsonO.getInt(Config.STR_OPERATION);
        }catch (JSONException e) {
            Log.e("jsonToContacts", Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
        return contacts;
    }
}
