package com.chrisjaunes.communication.client.contacts;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;


import com.chrisjaunes.communication.client.Config;
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

public class ContactsViewModel extends ViewModel {
    final private MutableLiveData<UniApiResult> uniApiResult = new MutableLiveData<>();
    public LiveData<UniApiResult> getUniApiResult() { return uniApiResult; }


    //UserDatabase userDatabase;
    //UserDatabaseDAO userDatabaseDAO;

    String UserId;

    public void setInfo(Context context,String userId){
//        this.context = context;
//        this.UserId = userId;
//        if(userDatabase == null){
//            userDatabase = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,"user_" + UserId).build();
//            userDatabaseDAO = userDatabase.getDao();
//        }
    }

    // 查询本地好友
    public void queryLocalFriends(){
//        new Thread(){
//            @Override
//            public void run(){
//                List<Contacts> friendShipList = userDatabaseDAO.queryAllFriends();
//                LocalFriendsListSingal.postValue(friendShipList);
//            }
//        }.start();
    }

    public void queryServer() {
//        final String lastTime = TimeHelper.getNowTime();
        final String lastTime = "0000-00-00 00:00:00";

        OkHttpClient client = OkHttpHelper.getClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(Config.STR_TIME, lastTime)
                .build();
        Request request = new okhttp3.Request.Builder()
                .post(requestBody)
                .url(Config.URL_CONTACTS_QUERY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, Arrays.toString(e.getStackTrace())));
                Log.e("Contacts", Config.ERROR_NET);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if( !response.isSuccessful()) {
                    uniApiResult.postValue(new UniApiResult.Fail(Config.ERROR_NET, String.valueOf(response.code())));
                    return;
                }
                String resJson = response.body().string();
                try {
                    JSONObject jsonO = new JSONObject(resJson);
                    JSONArray jsonA = (JSONArray) jsonO.get(Config.STR_STATUS_DATA);
                    List<Contacts> contactsList = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); ++i) {
                        Contacts contact = Contacts.jsonToContacts((JSONObject) jsonA.get(i));
                        contactsList.add(contact);
//                            friendShipList.add(contact);
//                            //查询本地数据库有无此好友，没有则 添加到本地
//                            Contacts friendShip_tem = null;
//                            friendShip_tem = userDatabaseDAO.searchFriend(friendShip.getAccount());
//                            if( friendShip_tem == null ){
//                                userDatabaseDAO.InsertFriend(friendShip);
//                            }
                    }
                    uniApiResult.postValue(new UniApiResult<>(jsonO.getString(Config.STR_STATUS), contactsList));
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
