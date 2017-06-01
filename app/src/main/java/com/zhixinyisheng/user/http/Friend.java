package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 好友
 * Created by gjj on 2016/10/20.
 */
public interface Friend {

    /**
     * 扫一扫加好友
     */
    @POST("friends/scan")
    @FormUrlEncoded
    Call<ResponseBody> sweep (@Field("zxys_userName")String phone,@Field("zxys_encrypt") String encrypt,@Field("fromuserid") String userID,@Field("touserid") String touserid);

    /**
     * 好友列表
     */
    @FormUrlEncoded
    @POST("friends/list")
    Call<ResponseBody> friend(@Field("zxys_userName") String phone,@Field("zxys_encrypt") String zxys_encrypt,@Field("fromuserid") String fromuserid);

    /**
     * 查看我的健康数据
     */
    @FormUrlEncoded
    @POST("userSystem/seeSet")
    Call<ResponseBody> lookhealth(@Field("zxys_userName") String phone,@Field("zxys_encrypt") String zxys_encrypt,
                                  @Field("userId") String userid,@Field("myData")String mydata);

    /**
     * 申请查看健康数据
     */
    @FormUrlEncoded
    @POST("friends/applyLook")
    Call<ResponseBody> applylook(@Field("zxys_userName") String phone,@Field("zxys_encrypt") String zxys_encrypt,
                                 @Field("fromuserid")String fromuserid,@Field("touserid")String touserid,@Field("language") String language);

    /**
     * 匹配通讯录中好友
     */
    @FormUrlEncoded
    @POST("friends/bookList")
    Call<ResponseBody> bookList(@Field("userId") String userId
            ,@Field("phone") String phones
            ,@Field("zxys_userName") String phone
            ,@Field("zxys_encrypt") String secret);
}
