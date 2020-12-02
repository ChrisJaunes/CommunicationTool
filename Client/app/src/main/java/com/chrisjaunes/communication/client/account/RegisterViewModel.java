package com.chrisjaunes.communication.client.account;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
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

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<UniApiResult<String>> result = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getResult() {
        return result;
    }

    public void register(final AccountInfo account, final String password) {
        OkHttpClient client = HttpHelper.getOkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_ACCOUNT, account.account)
                .add(Config.STR_PASSWORD, password)
                .add(Config.STR_NICKNAME, account.nickname)
                .add(Config.STR_AVATAR, account.avatar)
                .add(Config.STR_TEXT_STYLE, account.text_style.toJson())
                .build();

        Request request = new okhttp3.Request.Builder()
                .url(Config.URL_REGISTER)
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
                    Log.e("register", Config.ERROR_UNKNOW + response.code());
                    return;
                }
                String jsonS = response.body().string();
                Gson gson = new Gson();
                UniApiResult<String> res = gson.fromJson(jsonS, new TypeToken<UniApiResult<String>>() {}.getType());
                result.postValue(res);
                Log.v("register", res.status);
            }
        });
    }
}
