package com.chrisjaunes.communication.client.contacts;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.contacts.model.ContactsConfig;
import com.chrisjaunes.communication.client.contacts.model.ContactsDao;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.contacts.model.ContactsRetrofit;
import com.chrisjaunes.communication.client.contacts.model.ContactsViewManage;
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContactsViewModel extends ViewModel {
    private final MutableLiveData<UniApiResult<String>> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult<String>> getUniApiResult() { return uniApiResult; }

    private final MutableLiveData<List<String>> nowContactsListResult = new MutableLiveData<>();
    public LiveData<List<String>> getNowContactsListResult() { return nowContactsListResult; }
    private final MutableLiveData<List<String>> newContactsListResult = new MutableLiveData<>();
    public LiveData<List<String>> getNewContactsListResult() { return newContactsListResult; }

    private final ContactsDao contactsDao;
    private final ContactsViewManage contactsViewManage;
    public ContactsViewModel() {
        contactsDao = MyApplication.getInstance().getLocalDataBase().getContactsDao();
        contactsViewManage = ContactsViewManage.getInstance();
    }

    private List<String> updateContactsViewManage(List<ContactsRaw> contactsRawList) {
        List<String> resList = new ArrayList<>();
        for(ContactsRaw contactsRaw : contactsRawList) {
            contactsViewManage.setContactsRaw(contactsRaw);
            resList.add(contactsRaw.getAccount());
        }
        return resList;
    }
    public void queryLocalNowContactsList() {
        new Thread(() -> nowContactsListResult.postValue(updateContactsViewManage(contactsDao.queryNowContactsList()))).start();
    }
    public void queryLocalNewContactsList() {
        new Thread(() -> newContactsListResult.postValue(updateContactsViewManage(contactsDao.queryNewContactsList()))).start();
    }
    public List<ContactsRaw> updateLocalDataBase(final JSONArray contactsRawJsonList) throws JSONException {
        final List<ContactsRaw> contactsRawList = new ArrayList<>();
        for (int i = 0; i < contactsRawJsonList.length(); ++i) {
            final ContactsRaw contactsRaw = ContactsRaw.jsonToContactsRaw((JSONObject) contactsRawJsonList.get(i));
            if (!contactsDao.isNowContactsExist(contactsRaw.getAccount())) {
                contactsDao.InsertContacts(contactsRaw);
                contactsRawList.add(contactsRaw);
            } else {
                contactsDao.UpdateContacts(contactsRaw);
            }
        }
        return contactsRawList;
    }
    void updateContactsLiveData(final List<ContactsRaw> contactsRawList) {
        final List<String> nowContactsStringList = new ArrayList<>();
        final List<String> newContactsStringList = new ArrayList<>();
        for (ContactsRaw contactsRaw : contactsRawList) {
            if (ContactsConfig.CONTACTS_FRIENDS_AGREE_CODE == contactsRaw.getOperation()) {
                nowContactsStringList.add(contactsRaw.getAccount());
            }
            if (ContactsConfig.CONTACTS_FRIENDS_REQUEST_CODE == contactsRaw.getOperation()) {
                newContactsStringList.add(contactsRaw.getAccount());
            }
        }
        nowContactsListResult.postValue(nowContactsStringList);
        newContactsListResult.postValue(newContactsStringList);
    }
    public void queryServer() {
        //final String lastTime = TimeHelper.getLastTime();
        final String lastTime = "2020-11-17 13:14:20";
        final Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
        final Call<ResponseBody> call = retrofit.create(ContactsRetrofit.class).query(lastTime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                new Thread(()-> {
                    try {
                        final JSONObject apiJson = new JSONObject(response.body().string());
                        final String apiStatus = apiJson.getString(Config.STR_STATUS);
                        uniApiResult.postValue(new UniApiResult<>(apiStatus, apiStatus));
                        if (ContactsConfig.STATUS_QUERY_SUCCESSFUL.equals(apiStatus)) {
                            List<ContactsRaw> contactsRawList = updateLocalDataBase((JSONArray) apiJson.get(Config.STR_STATUS_DATA));
                            updateContactsLiveData(contactsRawList);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, Config.ERROR_UNKNOWN, Arrays.toString(e.getStackTrace())));
                    }
                }).start();
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
            }
        });
    }
}
