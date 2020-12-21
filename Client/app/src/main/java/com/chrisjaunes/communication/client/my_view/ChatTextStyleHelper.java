package com.chrisjaunes.communication.client.my_view;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Spinner;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.utils.ColorTrHelper;

public class ChatTextStyleHelper {
    private final View vi_font_color;
    private final View vi_bubble_color;
    private final View vi_border_color;

    public ChatTextStyleHelper(final View view) {
        vi_font_color = view.findViewById(R.id.vi_font_color);
        final Spinner sp_font_color = view.findViewById(R.id.sp_font_color);
        sp_font_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(view.getContext()));
        sp_font_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_font_color));

        vi_bubble_color = view.findViewById(R.id.vi_bubble_color);
        final Spinner sp_bubble_color = view.findViewById(R.id.sp_bubble_color);
        sp_bubble_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(view.getContext()));
        sp_bubble_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_bubble_color));

        vi_border_color = view.findViewById(R.id.vi_border_color);
        final Spinner sp_border_color = view.findViewById(R.id.sp_border_color);
        sp_border_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(view.getContext()));
        sp_border_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_border_color));
    }
    public ChatTextStyleRaw getChatTextStyle() {
        return new ChatTextStyleRaw.Builder().addChatFontColor(
                ColorTrHelper.colorToString(((ColorDrawable) vi_font_color.getBackground()).getColor())
        ).addChatBubbleColor(
                ColorTrHelper.colorToString(((ColorDrawable) vi_bubble_color.getBackground()).getColor())
        ).addChatBorderColor(
                ColorTrHelper.colorToString(((ColorDrawable) vi_border_color.getBackground()).getColor())
        ).build();
    }
}
