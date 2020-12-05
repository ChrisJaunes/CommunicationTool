package com.chrisjaunes.communication.client.contacts.model;

import com.chrisjaunes.communication.client.group.model.GInfo;
import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.group.model.GroupConfig;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactsRetrofit {
    @FormUrlEncoded
    @POST("QueryGroupMessages")
    Flowable<UniApiResult<List<GMessage>>> queryMessage(@Field("groupId") String groupId, @Field("time") String time);

    @FormUrlEncoded
    @POST("AddGroupMessage")
    Flowable<UniApiResult<String>> addMessage(@Field("groupId") String groupId, @Field("time") String time, @Field("1") int type, @Field("content") String content);

    @FormUrlEncoded
    @POST(ContactsConfig.URL_CONTACTS_QUERY)
    Call<ResponseBody> query(@Field(ContactsConfig.STR_TIME) String time);
}
