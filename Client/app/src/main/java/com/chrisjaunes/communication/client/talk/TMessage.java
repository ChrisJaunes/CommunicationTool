package com.chrisjaunes.communication.client.talk;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.contacts.Contacts;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

@Entity(primaryKeys = {"account1", "account2", "sendTime"})
public class TMessage {
    @NonNull
    private String account1;
    @NonNull
    private String account2;
    @NonNull
    private String sendTime;
    private int content_type;
    private String content;

    public TMessage(@NonNull String account1, @NonNull String account2, @NonNull String sendTime, int content_type) {
        this(account1, account2, sendTime, content_type, "");
        this.account1 = account1;
        this.account2 = account2;
        this.sendTime = sendTime;
        this.content_type = content_type;
    }
    @Ignore
    public TMessage(@NonNull String account1, @NonNull String account2, @NonNull String sendTime, int content_type, String content) {
        this.account1 = account1;
        this.account2 = account2;
        this.sendTime = sendTime;
        this.content_type = content_type;
        this.content = content;
    }

    @Ignore
    public static TMessage jsonToTMessage(JSONObject jsonO) {
        try {
            String account1 = jsonO.getString(Config.STR_ACCOUNT1);
            String account2 = jsonO.getString(Config.STR_ACCOUNT2);
            String sendTime = jsonO.getString(Config.STR_SEND_TIME);
            int content_type = jsonO.getInt(Config.STR_CONTENT_TYPE);
            TMessage message = new TMessage(account1, account2, sendTime, content_type);
            if (jsonO.has(Config.STR_CONTENT)) message.content = jsonO.getString(Config.STR_CONTENT);
            return message;
        }catch (JSONException e) {
            Log.e("jsonToContacts", Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            return new TMessage(Config.ACCOUNT_VISITORS, Config.ACCOUNT_VISITORS, "2020-11-29 00:00:00", 1);
        }
    }

    @NonNull
    public String getAccount1() { return account1; }

    public void setAccount1(@NonNull String account1) { this.account1 = account1; }

    @NonNull
    public String getAccount2() { return account2; }

    public void setAccount2(@NonNull String account2) { this.account2 = account2; }

    @NonNull
    public String getSendTime() { return sendTime; }

    public void setSendTime(@NonNull String sendTime) { this.sendTime = sendTime; }

    public int getContent_type() { return content_type; }

    public void setContent_type(int content_type) { this.content_type = content_type; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
