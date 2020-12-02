package com.chrisjaunes.communication.client.utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class HttpHelper {
    static private List<Cookie> cookies;
    static public OkHttpClient client;
    static public CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            HttpHelper.cookies = cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (null == cookies) return new ArrayList<>();
            return cookies;
        }
    };
    static public OkHttpClient getOkHttpClient() {
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();
        }
        return client;
    }
    static public Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder().client(client);
    }
}
