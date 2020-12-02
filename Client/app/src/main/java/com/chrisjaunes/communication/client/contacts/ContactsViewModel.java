package com.chrisjaunes.communication.client.contacts;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.contacts.model.ContactsDao;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.utils.HttpHelper;
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

public class ContactsViewModel extends ViewModel {
    final private MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }

    final private MutableLiveData<List<ContactsRaw>> nowContactsListResult = new MutableLiveData<>();
    public LiveData<List<ContactsRaw>> getNowContactsListResult() { return nowContactsListResult; }

    ContactsDao contactsDao;

    public ContactsViewModel() {
        contactsDao = MyApplication.getInstance().getLocalDataBase().getContactsDao();
    }

    public void queryLocalNowContactsList() {
        new Thread(() -> nowContactsListResult.postValue( contactsDao.queryNowContactsList())).start();
    }

    public void queryServer() {
        //final String lastTime = TimeHelper.getNowTime();
        final String lastTime = "0000-00-00 00:00:00";

        OkHttpClient client = HttpHelper.getOkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_TIME, lastTime)
                .build();
        Request request = new okhttp3.Request.Builder()
                .post(requestBody)
                .url(Config.URL_CONTACTS_QUERY)
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
                    List<ContactsRaw> nowContactsRawList = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); ++i) {
                        ContactsRaw contact = ContactsRaw.jsonToContacts((JSONObject) jsonA.get(i));
                        if (!contactsDao.isNowContactsExist(contact.getAccount())) {
                            contactsDao.InsertContacts(contact);
                            if (Config.CONTACTS_FRIENDS_AGREE_CODE == contact.getOperation()) {
                                nowContactsRawList.add(contact);
                            }
                        }
                    }
                    nowContactsListResult.postValue(nowContactsRawList);
                 } catch (JSONException e) {
                    e.printStackTrace();
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOW, Arrays.toString(e.getStackTrace())));
                }
            }
        });
    }

    // 获取最后更新当前好友的时间，并且更新最后更新的时间
    private String getLastUpdateNowFriendTime(String nowTime) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("user_" + UserId,Context.MODE_PRIVATE);
//        String lasTime = sharedPreferences.getString("LastUpdateNowFriendTime","1970-1-1 00:00:00");
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("LastUpdateNowFriendTime",nowTime);
//        editor.apply();
//        Log.d("lastime: ",nowTime);
//        return lasTime;
        return null;
    }
}
