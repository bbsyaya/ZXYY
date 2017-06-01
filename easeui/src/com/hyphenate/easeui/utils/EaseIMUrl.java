package com.hyphenate.easeui.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/11/2  15:59
 * <p>
 * 类说明: 请求接口类
 */
public interface EaseIMUrl {


    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("sysMessage/noReadMessage")
    Call<ResponseBody> getsysMessage(@Field("zxys_userName") String user_phone,
                                     @Field("zxys_encrypt") String zxys_encrypt,
                                     @Field("userId") String fromuserid);

    /**
     * 接受添加好友请求
     */
    @FormUrlEncoded
    @POST("friends/accpetFrineds")
    Call<ResponseBody> accpetFrineds(@Field("zxys_userName") String user_phone,
                                     @Field("zxys_encrypt") String zxys_encrypt,
                                     @Field("toUserId") String touserid,//传登录人ID
                                     @Field("fromUserId") String fromuserid,//好友id
                                     @Field("sysMessageId") String sysMessageId,
                                     @Field("language") String language);

    /**
     * 好友列表
     */
    @FormUrlEncoded
    @POST("friends/list")
    Call<ResponseBody> getMyFriends(@Field("zxys_userName") String user_phone,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("fromuserid") String fromuserid);


    /**
     * 接受添加好友请求
     */
    @FormUrlEncoded
    @POST("friends/agree")
    Call<ResponseBody> agreeLook(@Field("zxys_userName") String user_phone,
                                     @Field("zxys_encrypt") String zxys_encrypt,
                                     @Field("toUserId") String touserid,//传登录人ID
                                     @Field("fromUserId") String fromuserid,//好友id
                                     @Field("sysMessageId") String sysMessageId,
                                     @Field("language") String language);

    /**
     * 个人详情
     */
    @GET("myUser/selInfro")
    Call<ResponseBody> getMyinfo(@Query("zxys_userName") String user_phone,
                                 @Query("zxys_encrypt") String zxys_encrypt,
                                 @Query("userId") String userId);

}
