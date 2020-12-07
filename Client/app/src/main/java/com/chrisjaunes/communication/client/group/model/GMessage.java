package com.chrisjaunes.communication.client.group.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.group.GMessageViewModel;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

@Entity(primaryKeys = {"group", "account", "sendTime"})
public class GMessage {
    private int group;
    @NonNull
    private String account;
    @NonNull
    @SerializedName("send_time")
    private String sendTime;
    @SerializedName("content_type")
    private int contentType;
    private String content;

    @Ignore
    public GMessage(int group, @NonNull String account, @NonNull String sendTime, int contentType) {
        this(group, account, sendTime, contentType, "");
    }

    public GMessage(int group, @NonNull String account, @NonNull String sendTime, int contentType, String content) {
        this.group = group;
        this.account = account;
        this.sendTime = sendTime;
        this.contentType = contentType;
        this.content = content;
    }

    @Ignore
    public static GMessage jsonToTMessage(JSONObject jsonO) {
        try {
            String group = jsonO.getString(GroupConfig.STR_GROUP);
            String account = jsonO.getString(GroupConfig.STR_ACCOUNT);
            String send_time = jsonO.getString(GroupConfig.STR_SEND_TIME);
            int content_type = jsonO.getInt(GroupConfig.STR_CONTENT_TYPE);
            String content = jsonO.getString(GroupConfig.STR_CONTENT);
            return new GMessage(Integer.parseInt(group), account, send_time, content_type, content);
        }catch (JSONException  | NumberFormatException e) {
            Log.e("jsonToContacts", Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            return new GMessage(0, Config.ACCOUNT_VISITORS, "2020-11-29 00:00:00", 1);
        }
    }

    public int getGroup() { return group; }

    public void setGroup(int group) { this.group = group; }

    @NonNull
    public String getAccount() { return account; }

    public void setAccount(@NonNull String account) { this.account = account; }

    @NonNull
    public String getSendTime() { return sendTime; }

    public void setSendTime(@NonNull String sendTime) { this.sendTime = sendTime; }

    public int getContentType() { return contentType; }

    public void setContentType(int contentType) { this.contentType = contentType; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
