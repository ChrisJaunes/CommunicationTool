package com.chrisjaunes.communication.client.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chrisjaunes.communication.client.R;

import java.util.ArrayList;

public class ColorTrHelper {
    public static String colorToString(int color) {
        String s = "#";
        int colorStr = (color & 0xff000000) | (color & 0x00ff0000) | (color & 0x0000ff00) | (color & 0x000000ff);
        s = s + Integer.toHexString(colorStr);
        return s;
    }
    public static String nameToColor(String name){
        switch (name){
            case "红色":
                return "#ff0000";
            case "黄色":
                return "#ffff00";
            case "紫色":
                return "#ee82ee";
            case "黑色":
                return "#000000";
            case "橙色":
                return "#ffa500";
            default:
                return "#f5f5f5";
        }
    }

    public static class ColorSelectedAdapter {
        private static final ArrayList<String> ColorList;
        static {
            ColorList = new ArrayList<>();
            ColorList.add("红色");
            ColorList.add("黄色");
            ColorList.add("紫色");
            ColorList.add("黑色");
            ColorList.add("橙色");
        }
        public static ArrayAdapter<String> getColorAdapter(Context context) {
            return new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,ColorList);
        }
    }
    public static class ColorSelectedListener implements AdapterView.OnItemSelectedListener {
        View color_view;
        public ColorSelectedListener(final View view) {
            color_view = view;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String content = parent.getItemAtPosition(position).toString();
            String ColorStr = ColorTrHelper.nameToColor(content);
            int color = Color.parseColor(ColorStr);
            color_view.setBackgroundColor(color);
            Log.d("TMP", "" + color_view + "|" + color_view.getBackground() );
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}
