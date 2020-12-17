package com.chrisjaunes.communication.client.myView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatTextStyleRaw {
    private static final String STR_FONT_COLOR = "font_color";
    private static final String STR_BUBBLE_COLOR = "bubble_color";
    private static final String STR_BORDER_COLOR = "border_color";
    private static final String DEFAULT_FONT_COLOR = "#ff000000";
    private static final String DEFAULT_BUBBLE_COLOR = "#ffffffff";
    private static final String DEFAULT_BORDER_COLOR = "#ffffffff";
    public static ChatTextStyleRaw CHAT_TEXT_STYLE_RAW_DEFAULT = new ChatTextStyleRaw();

    public String font_color = ChatTextStyleRaw.DEFAULT_FONT_COLOR;
    public String bubble_color = ChatTextStyleRaw.DEFAULT_BUBBLE_COLOR;
    public String border_color = ChatTextStyleRaw.DEFAULT_BORDER_COLOR;

    @NonNull
    @Override
    public String toString() {
        return "AccountTextStyle{" +
                "font_color='" + font_color + '\'' +
                ", bubble_color='" + bubble_color + '\'' +
                ", border_color='" + border_color + '\'' +
                '}';
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ChatTextStyleRaw valueOf(String json) {
        ChatTextStyleRaw chatTextStyle = new ChatTextStyleRaw();
        try {
            if (null == json) json = "{}";
            JSONObject jsonO = new JSONObject(json);
            if (jsonO.has(STR_FONT_COLOR))
                chatTextStyle.font_color = jsonO.getString(STR_FONT_COLOR);
            if (jsonO.has(STR_BUBBLE_COLOR))
                chatTextStyle.bubble_color = jsonO.getString(STR_BUBBLE_COLOR);
            if (jsonO.has(STR_BORDER_COLOR))
                chatTextStyle.border_color = jsonO.getString(STR_BORDER_COLOR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chatTextStyle;
    }

    public static class Builder {
        ChatTextStyleRaw chatTextStyle;

        public Builder() {
            chatTextStyle = new ChatTextStyleRaw();
        }

        public Builder addChatFontColor(String color) {
            chatTextStyle.font_color = color;
            return this;
        }

        public Builder addChatBubbleColor(String color) {
            chatTextStyle.bubble_color = color;
            return this;
        }

        public Builder addChatBorderColor(String color) {
            chatTextStyle.border_color = color;
            return this;
        }

        ChatTextStyleRaw build() {
            return chatTextStyle;
        }
    }
}

