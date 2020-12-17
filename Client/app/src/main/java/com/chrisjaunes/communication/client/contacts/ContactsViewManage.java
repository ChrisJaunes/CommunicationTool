package com.chrisjaunes.communication.client.contacts;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.contacts.model.ContactsDao;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.contacts.model.ContactsRetrofit;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;
import com.chrisjaunes.communication.client.utils.HttpHelper;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 设计模式：单例模式（懒汉模式）、享元模式
 * version 1.1 : 使用了单例模式
 * 持有 ContactsRaw，处于编码简便的考虑，没有单独出一个接口
 * version 1.2 : CONTACTS_VIEW_DEFAULT
 * @author chrisjuanes
 * @version 1.2
 */
public class ContactsViewManage {
    private static ContactsViewManage instance;
    private final ContactsDao contactsDao;
    private ContactsViewManage (){
        contactsDao = MyApplication.getInstance().getLocalDataBase().getContactsDao();
    }
    public static synchronized ContactsViewManage getInstance() {
        if (null == instance) instance = new ContactsViewManage();
        return instance;
    }

    private final HashMap<String, ContactsView> contactsViewHashMap = new HashMap<>();
    public void setContactsRaw(@NonNull final ContactsRaw contactsRaw) {
        String account = contactsRaw.getAccount();
        if(contactsViewHashMap.containsKey(account)) {
            Objects.requireNonNull(contactsViewHashMap.get(account)).setContactsRaw(contactsRaw);
        }else{
            contactsViewHashMap.put(account, new ContactsView(contactsRaw));
        }
    }
    // TODO visit accountRaw and modify local database if account is in exist;
    public ContactsView getContactsView(String account) {
        if(contactsViewHashMap.containsKey(account)) return contactsViewHashMap.get(account);
        new Thread(()-> {
            Retrofit retrofit = HttpHelper.getRetrofitBuilder().baseUrl(Config.URL_BASE).build();
            Call<ResponseBody> call = retrofit.create(ContactsRetrofit.class).queryAccount(account);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                }
            });
        }).start();
        return ContactsView.CONTACTS_VIEW_DEFAULT;
    }
    public ContactsRaw getContactsRaw(String account) {
        if(contactsViewHashMap.containsKey(account)) return Objects.requireNonNull(contactsViewHashMap.get(account)).getContactsRaw();
        return contactsDao.queryContactsByAccountID(account);
    }
}
