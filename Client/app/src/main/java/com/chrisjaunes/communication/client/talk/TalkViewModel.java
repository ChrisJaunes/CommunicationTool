package com.chrisjaunes.communication.client.talk;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;


import com.chrisjaunes.communication.client.utils.OkHttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TalkViewModel extends ViewModel {
//    private MutableLiveData<List<Messages> > LocalMessageSingal;
//    private MutableLiveData<List<Messages> > ServeMessageSingal;
//    private MutableLiveData<FriendShip> UpdateInfoSingal;
//
//    private String UserId;
//    private String TalkUserId;
//    private UserDatabase userDatabase;
//    private UserDatabaseDAO userDatabaseDAO;
//    private Context context;
//
//    public void setInfo( String userId,String talkUserId,Context context){
//        this.UserId = userId;
//        this.TalkUserId = talkUserId;
//        this.context = context;
//        if(userDatabase == null){
//            userDatabase = Room.databaseBuilder(context,UserDatabase.class,"user_" + UserId).build();
//            userDatabaseDAO = userDatabase.getDao();
//        }
//    }
//
//    public MutableLiveData<List<Messages>> getLocalMessageSingal(){
//        if( LocalMessageSingal == null){
//            LocalMessageSingal = new MutableLiveData<>();
//        }
//        return LocalMessageSingal;
//    }
//
//    public MutableLiveData<List<Messages>> getServeMessageSingal(){
//        if( ServeMessageSingal == null){
//            ServeMessageSingal = new MutableLiveData<>();
//        }
//        return ServeMessageSingal;
//    }
//
//    public MutableLiveData<FriendShip> getUpdateInfoSingal(){
//        if(UpdateInfoSingal == null){
//            UpdateInfoSingal = new MutableLiveData<>();
//        }
//        return UpdateInfoSingal;
//    }
//
//    // 查询本地数据库信息
//    public void queryLocalMessage(){
//        new Thread(){
//            @Override
//            public void run(){
//                List<Messages> messagesList = userDatabaseDAO.queryAllMessage(TalkUserId);
//                LocalMessageSingal.postValue(messagesList);
//            }
//        }.start();
//    }
//
//    // 查询服务器上的信息
    public void queryServer(){
//        String lastTime = getLastUpdateMessageTime();
//        String url = MyApplication.getMyApplicationInstance().ip + "QueryMessages";
        OkHttpClient client = OkHttpHelper.getClient();
        RequestBody requestBody = new FormBody.Builder()
                                        .add("user1",UserId).add("user2",TalkUserId)
                                        .add("time",lastTime).build();
        final Request request = new okhttp3.Request.Builder().post(requestBody).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if( response.isSuccessful()){
                    String resStr = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(resStr);
                        JSONArray jsonArray = (JSONArray) jsonObject.get("list");
                        List<Messages> messageList = new ArrayList<>();
                        for(int i = 0;i<jsonArray.length();++i){
                            JSONObject json = (JSONObject) jsonArray.get(i);
                            Messages messages = new Messages(json.getString("from_user") ,json.getString("to_user") ,
                                                                json.getString("time") , json.getString("content"));
                            // 服务器数据先存到list ， 然后livedata 返回回去给界面
                            //检查是否存在本地，没有才保存到本地
                            Messages testMess = null;
                            testMess = userDatabaseDAO.searchMessage(messages.getFromUserId(),messages.getToUserId(),messages.getSendTime(),messages.getContent());
                            if( testMess == null){
                                userDatabaseDAO.InsertMessage(messages);
                                // 如果本地不存在，说明recyclerview没有，这样才加到recyclerview里面去
                                messageList.add(messages);
                            }
                        }
                        ServeMessageSingal.postValue(messageList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
//    // 发送消息
//    public void addNewMessage(final Messages messages){
//        // 先加到服务器那边
//        String url = MyApplication.getMyApplicationInstance().ip + "AddMessages";
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = new FormBody.Builder().add("from_user",messages.getFromUserId())
//                                        .add("to_user",messages.getToUserId()).add("time",messages.getSendTime())
//                                        .add("content",messages.getContent()).build();
//        Request request = new okhttp3.Request.Builder().post(requestBody).url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) { }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException { }
//        });
//        // 再加到本地数据库
//        new Thread(){
//            @Override
//            public void run(){
//                userDatabaseDAO.InsertMessage(messages);
//            }
//        }.start();
//    }
}
