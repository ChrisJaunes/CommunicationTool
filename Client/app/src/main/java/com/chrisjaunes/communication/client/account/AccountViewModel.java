package com.chrisjaunes.communication.client.account;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.myView.ChatTextStyleRaw;
import com.chrisjaunes.communication.client.utils.BitmapHelper;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 采用了MVVM,作为ViewModel层
 * version 1.1: use okhttp3
 * @author ChrisJaunes
 * @version 1.1
 */
public class AccountViewModel extends ViewModel {
    MutableLiveData<UniApiResult<String>> result = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getResult() {
        return result;
    }

    public void updateAvatar(@NonNull final Bitmap avatar) {
        final String avatarString = BitmapHelper.BitmapToString(avatar);
        Log.i("UpdateMessage","bitmap len:" + avatarString.length());

        OkHttpClient client = HttpHelper.getOkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_AVATAR, avatarString)
                .build();
        Request request = new okhttp3.Request.Builder()
                .url(Config.URL_UPDATE_MESSAGE)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                result.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Login", Config.ERROR_NET);
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    result.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, String.format("错误返回代码 %d", response.code())));
                    Log.e("Login", Config.ERROR_UNKNOWN + response.code());
                    return;
                }
                assert response.body() != null;
                String jsonS = response.body().string();
                UniApiResult<String> res = new Gson().fromJson(jsonS, new TypeToken<UniApiResult<String>>() {}.getType());
                Log.v("Login", res.status);
                result.postValue(res);
                if(Config.STATUS_UPDATE_SUCCESSFUL.equals(res.status))
                    AccountViewManage.getInstance().getAccountView().setAvatarView(avatar);
            }
        });
    }

    public void updateTextStyle(final ChatTextStyleRaw textStyle) {
        OkHttpClient client = HttpHelper.getOkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_TEXT_STYLE, textStyle.toJson())
                .build();

        Request request = new okhttp3.Request.Builder()
                .url(Config.URL_UPDATE_MESSAGE)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                result.postValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    result.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, Config.ERROR_UNKNOWN, String.format("错误返回代码 %d", response.code())));
                    return;
                }
                assert response.body() != null;
                String jsonS = response.body().string();
                Gson gson = new Gson();
                UniApiResult<String> res = gson.fromJson(jsonS, new TypeToken<UniApiResult<String>>() {
                }.getType());
                result.postValue(res);
            }
        });
    }
}
