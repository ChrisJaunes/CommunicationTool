package com.chrisjaunes.communication.client.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class OkHttpHelper {
    static private List<Cookie> cookies;
    static public OkHttpClient client;
    static public CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            OkHttpHelper.cookies = cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (null == cookies) return new ArrayList<>();
            return cookies;
        }
    };
    static public OkHttpClient getClient() {
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();
        }
        return client;
    }
}
