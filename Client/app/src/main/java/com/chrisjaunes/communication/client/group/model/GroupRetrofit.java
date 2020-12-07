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
    @POST(GroupConfig.URL_GROUP_QUERY_MESSAGE)
    Flowable<UniApiResult<List<GMessage>>> queryMessage(@Field(GroupConfig.STR_GROUP) String groupId,
                                                        @Field(GroupConfig.STR_TIME) String time);

    @FormUrlEncoded
    @POST(GroupConfig.URL_GROUP_ADD_MESSAGE)
    Flowable<UniApiResult<String>> addMessage(@Field(GroupConfig.STR_GROUP) String groupId,
                                              @Field(GroupConfig.STR_SEND_TIME) String time,
                                              @Field(GroupConfig.STR_CONTENT_TYPE) int type,
                                              @Field(GroupConfig.STR_CONTENT) String content);

    @POST(GroupConfig.URL_GROUP_QUERY)
    Flowable<UniApiResult<List<GInfo>>> query();

    @FormUrlEncoded
    @POST(GroupConfig.URL_GROUP_ADD)
    Call<ResponseBody> addGroup(@Field(GroupConfig.STR_GROUP_NAME) String groupName, @Field(GroupConfig.STR_GROUP_MEMBER_LIST) String members);
}
