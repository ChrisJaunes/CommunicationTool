package com.chrisjaunes.communication.client.group.model;

import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
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

    @FormUrlEncoded
    @POST(GroupConfig.URL_GROUP_ADD)
    Call<ResponseBody> addGroup(@Field(GroupConfig.STR_GROUP_NAME) String groupName, @Field(GroupConfig.STR_GROUP_MEMBER_LIST) String members);
}
