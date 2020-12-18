package com.chrisjaunes.communication.client.account;
import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.MD5Helper;
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
 * view model about login, use live data to observe, use okhttp3
 * status： XXX
 * STR_PASSWORD and STR_PASSWORD must equal to server.account.login#STR_PASSWORD and server.account.login#STR_PASSWORD
 * @author ChrisJaunes
 * @version 1
 */
public class LoginViewModel extends ViewModel {
    public static final String STR_ACCOUNT = "account";
    public static final String STR_PASSWORD = "password";
    static final public String STATUS_LOGIN_SUCCESSFUL = "login successful";
    static final public String STATUS_LOGIN_ACCOUNT_ERROR = "account is not exist";
    static final public String STATUS_LOGIN_PASSWORD_ERROR = "password is not correct";

    private final MutableLiveData<UniApiResult<String>> uniApiResultLiveDate = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResultLiveDate() { return uniApiResultLiveDate; }
    private final MutableLiveData<AccountRaw> accountRawLiveData = new MutableLiveData<>();
    public LiveData<AccountRaw> getAccountRawLiveData() {return accountRawLiveData;}

    public void login(final String account,final String password) {
        final String password_e = MD5Helper.MD5(password);
        OkHttpClient client = HttpHelper.getOkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add(STR_ACCOUNT,  account)
                .add(STR_PASSWORD, password_e)
                .build();

        Request request = new okhttp3.Request.Builder()
                .url(Config.URL_LOGIN)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                uniApiResultLiveDate.postValue(new UniApiResult.Fail(Config.STATUS_NET_ERROR, Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
            }
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    uniApiResultLiveDate.postValue(new UniApiResult.Fail(Config.STATUS_NET_ERROR, Config.ERROR_UNKNOWN, String.format("错误返回代码 %d", response.code())));
                    return;
                }
                assert response.body() != null;
                String jsonS = response.body().string();
                Log.d(">>", jsonS);
                Gson gson = new Gson();
                UniApiResult<AccountRaw> res = gson.fromJson(jsonS, new TypeToken<UniApiResult<AccountRaw>>() {}.getType());
                uniApiResultLiveDate.postValue(new UniApiResult<>(res.status, res.status));
                if (STATUS_LOGIN_SUCCESSFUL.equals(res.status)) {
                    accountRawLiveData.postValue(res.data);
                    MyApplication.getInstance().setLoginAuto(account, password);
                }
            }
        });
    }
}
