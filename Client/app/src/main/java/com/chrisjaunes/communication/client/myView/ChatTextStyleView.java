package com.chrisjaunes.communication.client.myView;

import android.graphics.Color;

public class ChatTextStyleView {
    public static final ChatTextStyleView CHAT_TEXT_STYLE_DEFAULT = new ChatTextStyleView();
    public int text_color;
    public int border_color;
    public int bubble_color;

    public static ChatTextStyleView valueOf(ChatTextStyleRaw rawData) {
        ChatTextStyleView viewData = new ChatTextStyleView();
        viewData.text_color = Color.parseColor(rawData.font_color);
        viewData.border_color = Color.parseColor(rawData.border_color);
        viewData.bubble_color = Color.parseColor(rawData.bubble_color);
        return viewData;
    }
    public static ChatTextStyleView valueOf(String json) {
        return valueOf(ChatTextStyleRaw.valueOf(json));
    }
}
