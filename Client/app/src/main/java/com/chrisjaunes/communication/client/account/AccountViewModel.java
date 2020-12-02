package com.chrisjaunes.communication.client.account;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
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

public class AccountViewModel extends ViewModel {
    MutableLiveData<UniApiResult<String>> result = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getResult() {
        return result;
    }

    public void updateAvatar(@NonNull final Bitmap avatar) {
        final String avatarString = BitmapHelper.BitmapToString(avatar);
        Log.i("UpdateMessage","bitmap len:" + avatarString.length());
        int needBytes = avatarString.length() * 2;
        if ( needBytes > Config.LIMIT_AVATAR_LEN) {
            result.postValue(new UniApiResult.Fail(Config.ERROR_AVATAR_TOO_LAEGE, null));
            return;
        }
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
            public void onFailure(Call call, IOException e) {
                result.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Login", Config.ERROR_NET);
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    result.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOW, String.format("错误返回代码 %d", response.code())));
                    Log.e("Login", Config.ERROR_UNKNOW + response.code());
                    return;
                }
                String jsonS = response.body().string();
                UniApiResult<String> res = new Gson().fromJson(jsonS, new TypeToken<UniApiResult<String>>() {}.getType());
                Log.v("Login", res.status);
                result.postValue(res);
                if(Config.STATUS_UPDATE_SUCCESSFUL.equals(res.status)) AccountViewManage.getInstance().setAvatar(avatar);
            }
        });
    }

    public void updateTextStyle(final ChatTextStyle textStyle) {
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
            public void onFailure(Call call, IOException e) {
                result.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Login", Config.ERROR_NET);
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    result.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOW, String.format("错误返回代码 %d", response.code())));
                    Log.e("Login", Config.ERROR_UNKNOW + response.code());
                    return;
                }
                String jsonS = response.body().string();
                Gson gson = new Gson();
                UniApiResult<String> res = gson.fromJson(jsonS, new TypeToken<UniApiResult<String>>() {
                }.getType());
                result.postValue(res);
                Log.v("Login", res.status);
            }
        });
    }
    public void logout() {

    }
}
