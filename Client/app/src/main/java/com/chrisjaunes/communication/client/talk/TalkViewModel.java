package com.chrisjaunes.communication.client.talk;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.account.AccountManage;
import com.chrisjaunes.communication.client.utils.OkHttpHelper;
import com.chrisjaunes.communication.client.utils.TimeHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TalkViewModel extends ViewModel {
    final private MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }
    private final MutableLiveData<List<TMessage>> TMessageList = new MutableLiveData<>();
    public LiveData<List<TMessage>> getTMessageList() { return TMessageList; }

    final private TMessageDao tMessageDao;
    final private String contacts_account;
    TalkViewModel(final String contacts_account) {
        tMessageDao = MyApplication.getInstance().getLocalDataBase().getTalkMessageDao();
        this.contacts_account = contacts_account;
    }

    public void queryLocalMessageList() {
        new Thread(() -> TMessageList.postValue(tMessageDao.queryMessageAboutTalk(contacts_account))).start();
    }
    public void updateMessage(final int type, final String content) {
        final String lastTime = TimeHelper.getNowTime();
        final OkHttpClient client = OkHttpHelper.getClient();
        final RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_ACCOUNT2, contacts_account)
                .add(Config.STR_SEND_TIME, lastTime)
                .add(Config.STR_CONTENT_TYPE, String.valueOf(type))
                .add(Config.STR_CONTENT, content)
                .build();
        final Request request = new okhttp3.Request.Builder()
                .post(requestBody)
                .url(Config.URL_TALK_UPDATE)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Contacts", Config.ERROR_NET);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if( !response.isSuccessful() ) {
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                try {
                    String resJson = response.body().string();
                    JSONObject jsonO = new JSONObject(resJson);
                    uniApiResult.postValue(new UniApiResult<>(jsonO.getString(Config.STR_STATUS), ""));
                    if (Config.STATUS_UPDATE_SUCCESSFUL.equals(jsonO.getString(Config.STR_STATUS))) {
                        Log.d(">>>", "" + content);
                        TMessage tMessage = new TMessage(AccountManage.getInstance().getAccount(),
                                contacts_account, lastTime, type, content);
                        tMessageDao.InsertMessage(tMessage);
                        List<TMessage> newTMessages = new ArrayList<>();
                        newTMessages.add(tMessage);
                        TMessageList.postValue(newTMessages);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void queryServer(){
        //final String lastTime = TimeHelper.getNowTime();
        final String lastTime = "0000-00-00 00:00:00";
        final OkHttpClient client = OkHttpHelper.getClient();
        final RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_TIME, lastTime)
                .build();
        final Request request = new okhttp3.Request.Builder()
                .post(requestBody)
                .url(Config.URL_TALK_QUERY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Contacts", Config.ERROR_NET);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if( !response.isSuccessful() ) {
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                String resJson = response.body().string();
                try {
                    JSONObject jsonO = new JSONObject(resJson);
                    uniApiResult.postValue(new UniApiResult<>(jsonO.getString(Config.STR_STATUS), ""));

                    JSONArray jsonA = (JSONArray) jsonO.get(Config.STR_STATUS_DATA);
                    List<TMessage> newTMessages = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); ++i) {
                        TMessage message = TMessage.jsonToTMessage((JSONObject) jsonA.get(i));
                        if (!tMessageDao.isMessageExist(message.getAccount1(), message.getAccount2(),message.getSendTime())) {
                            tMessageDao.InsertMessage(message);
                            newTMessages.add(message);
                        }
                    }
                    TMessageList.postValue(newTMessages);
                } catch (JSONException e) {
                    e.printStackTrace();
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOW, Arrays.toString(e.getStackTrace())));
                }
            }
        });
    }
//
//    // 更新好友的信息，头像或者textstyle
//    public void updateTalkInfo(String userId){
//        String url = MyApplication.getMyApplicationInstance().ip + "QueryAccountInfo";
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = new FormBody.Builder().add("user",userId).build();
//        Request request = new okhttp3.Request.Builder().post(requestBody).url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) { }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){
//                    String resStr = response.body().string();
//                    try {
//                        JSONObject jsonObject = new JSONObject(resStr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                else UpdateInfoSingal.postValue(null);
//            }
//        });
//    }
//
//    // 获取最后一次更新该好友消息的时间，并且重置时间
//    private String getLastUpdateMessageTime(){
//        SharedPreferences sharedPreferences = context.getSharedPreferences("user_" + UserId + "_talker_" + TalkUserId,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String lastTime = sharedPreferences.getString("LastUpdateMessageTime","1970-1-1 00:00:00");
//        editor.putString("LastUpdateMessageTime", MyApplication.getMyApplicationInstance().getNowTime());
//        editor.commit();
//        return lastTime;
//    }
//
    static class Factory implements ViewModelProvider.Factory {
        final String contacts_account;

        Factory(final String contacts_account) {
            this.contacts_account = contacts_account;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TalkViewModel(contacts_account);
        }
    }
}
