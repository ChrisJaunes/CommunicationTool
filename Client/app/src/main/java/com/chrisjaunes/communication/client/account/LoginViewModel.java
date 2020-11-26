package com.chrisjaunes.communication.client.account;
import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.utils.OkHttpHelper;
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

public class LoginViewModel extends ViewModel{
    private final MutableLiveData<UniApiResult> result = new MutableLiveData<>();
    public LiveData<UniApiResult> getResult() {
        return result;
    }

    public void login(final String account,final String password) {
        OkHttpClient client = OkHttpHelper.getClient();

        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_ACCOUNT,  account)
                .add(Config.STR_PASSWORD, password)
                .build();

        Request request = new okhttp3.Request.Builder()
                .url(Config.URL_LOGIN)
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
                UniApiResult<Account> res = gson.fromJson(jsonS, new TypeToken<UniApiResult<Account>>() {}.getType());
                result.postValue(res);
                Log.v("Login", res.status);
                Log.v("Login", "" + res.data);
            }
        });
    }

}
