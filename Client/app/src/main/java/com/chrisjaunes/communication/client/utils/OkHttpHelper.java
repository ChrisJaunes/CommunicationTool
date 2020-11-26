package com.chrisjaunes.communication.client.utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class OkHttpHelper {
    static public OkHttpClient client;
    static public CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            ArrayList<Cookie> cookies = new ArrayList<>();
            Cookie cookie = new Cookie.Builder()
                    .hostOnlyDomain(url.host())
                    .name("SESSION").value("zyao89")
                    .build();
            cookies.add(cookie);
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
