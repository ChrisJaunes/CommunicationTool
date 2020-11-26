package com.chrisjaunes.communication.client.utils;

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
}
