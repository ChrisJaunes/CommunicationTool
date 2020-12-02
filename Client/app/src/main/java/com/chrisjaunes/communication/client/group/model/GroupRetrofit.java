package com.chrisjaunes.communication.client.group.model;

import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GroupRetrofit {
    @FormUrlEncoded
    @POST("QueryGroupMessages")
    Flowable<UniApiResult<List<GMessage>>> queryMessage(@Field("groupId") String groupId, @Field("time") String time);

    @FormUrlEncoded
    @POST("AddGroupMessage")
    Flowable<UniApiResult<String>> addMessage(@Field("groupId") String groupId, @Field("time") String time, @Field("1") int type, @Field("content") String content);

    @POST(GroupConfig.URL_GROUP_QUERY)
    Flowable<UniApiResult<List<GInfo>>> query();

}
