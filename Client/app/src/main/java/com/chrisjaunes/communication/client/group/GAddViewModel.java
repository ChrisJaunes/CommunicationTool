package com.chrisjaunes.communication.client.group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.group.model.GroupRetrofit;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
/**
 * @author ChrisJaunes
 * @version 1
 */
public class GAddViewModel extends ViewModel {
    static public final String CREATE_SUCCESSFUL = "1";
    static public final String CREATE_FAIL = "2";

    private final MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }

    // TODO 添加群聊
    public void addGroup(final String groupName, List<String> groupMember) {
        Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
        Call<ResponseBody> call = retrofit.create(GroupRetrofit.class).addGroup(groupName, new Gson().toJson(groupMember));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // TODO
                uniApiResult.postValue(new UniApiResult<>(CREATE_SUCCESSFUL, "创建成功"));
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
            }
        });
    }
}
