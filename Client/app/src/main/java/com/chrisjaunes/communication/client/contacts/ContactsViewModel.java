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
import com.chrisjaunes.communication.client.utils.HttpHelper;
import com.chrisjaunes.communication.client.utils.ThreadPoolHelper;
import com.chrisjaunes.communication.client.utils.TimeHelper;
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

/**
 * 采用了MVVM,作为ViewModel层
 * version 1.1: use Retrofit
 * @author ChrisJaunes
 * @version 1.1
 */
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
        nowContactsListResult.postValue(updateContactsViewManage(contactsDao.queryNowContactsList()));
    }
    public void queryLocalNewContactsList() {
        newContactsListResult.postValue(updateContactsViewManage(contactsDao.queryNewContactsList()));
    }
    private void updateLocalDataBase(final JSONArray contactsRawJsonList) throws JSONException {
        for (int i = 0; i < contactsRawJsonList.length(); ++i) {
            final ContactsRaw contactsRaw = ContactsRaw.jsonToContactsRaw((JSONObject) contactsRawJsonList.get(i));
            if (!contactsDao.isNowContactsExist(contactsRaw.getAccount())) {
                contactsDao.InsertContacts(contactsRaw);
            } else {
                contactsDao.UpdateContacts(contactsRaw);
            }
        }
    }
    public void queryServer() {
        //final String lastTime = TimeHelper.getLastTime();
        final String lastTime = "2020-11-17 00:00:00";
        final Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
        final Call<ResponseBody> call = retrofit.create(ContactsRetrofit.class).query(lastTime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                ThreadPoolHelper.getInstance().execute(() -> {
                    try {
                        assert response.body() != null;
                        final JSONObject apiJson = new JSONObject(response.body().string());
                        final String apiStatus = apiJson.getString(Config.STR_STATUS);
                        uniApiResult.postValue(new UniApiResult<>(apiStatus, apiStatus) );
                        if (ContactsConfig.STATUS_QUERY_SUCCESSFUL.equals(apiStatus)) {
                            updateLocalDataBase((JSONArray) apiJson.get(Config.STR_STATUS_DATA));
                            queryLocalNowContactsList();
                            queryLocalNewContactsList();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, Config.ERROR_UNKNOWN, Arrays.toString(e.getStackTrace())));
                    };
                });
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
            }
        });
    }
    public void handleRequestContacts(final String account, final String operation) {
        final String time = TimeHelper.getNowTime();
        final Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
        final Call<ResponseBody> call = retrofit.create(ContactsRetrofit.class).update(account, time, operation);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                ThreadPoolHelper.getInstance().execute(() -> {
                    try {
                        assert response.body() != null;
                        final JSONObject apiJson = new JSONObject(response.body().string());
                        final String apiStatus = apiJson.getString(Config.STR_STATUS);
                        uniApiResult.postValue(new UniApiResult<>(apiStatus, apiStatus));
                        if (ContactsConfig.STATUS_UPDATE_SUCCESSFUL.equals(apiStatus)) {
                            ContactsRaw contactsRaw = contactsViewManage.getContactsRaw(account);
                            if (ContactsConfig.CONTACTS_FRIENDS_AGREE.equals(operation)) {
                                contactsRaw.setOperation(ContactsConfig.CONTACTS_FRIENDS_AGREE_CODE);
                            } else {
                                contactsRaw.setOperation(ContactsConfig.CONTACTS_FRIENDS_NULL_CODE);
                            }
                        }
                        queryLocalNewContactsList();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, Config.ERROR_UNKNOWN, Arrays.toString(e.getStackTrace())));
                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
            }
        });
    }

    /**
     * TODO 本处的的更新判断尚未写完，将在下一次迭代中完成
     * @author ChrisJaunes
     * @param account
     */
    public void addContacts(final String account) {
        final String time = TimeHelper.getNowTime();
        final Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
        final Call<ResponseBody> call = retrofit.create(ContactsRetrofit.class).update(account, time, ContactsConfig.CONTACTS_FRIEND_REQUEST);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                ThreadPoolHelper.getInstance().execute(() -> {
                    try {
                        assert response.body() != null;
                        final JSONObject apiJson = new JSONObject(response.body().string());
                        final String apiStatus = apiJson.getString(Config.STR_STATUS);
                        if(ContactsConfig.STATUS_UPDATE_SUCCESSFUL.equals(apiStatus)) {
                            uniApiResult.postValue(new UniApiResult<>(apiStatus, "请求成功"));
                        } else{
                            uniApiResult.postValue(new UniApiResult<>(apiStatus, "已经进行过请求或无此用户"));
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_UNKNOWN, Config.ERROR_UNKNOWN, Arrays.toString(e.getStackTrace())));
                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                uniApiResult.setValue(new UniApiResult.Fail(Config.ERROR_NET, Config.ERROR_NET, Arrays.toString(t.getStackTrace())));
            }
        });
    }
}
