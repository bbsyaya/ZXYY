package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * sos
 * Created by 焕焕 on 2016/11/10.
 */
public interface SosUrl {
    /**
     * 发起呼救
     */
    @GET("userHelp/sendHelp")
    Call<ResponseBody> sendHelp(@Query("userId") String userId
            , @Query("localX") String localX
            , @Query("localY") String localY
            , @Query("Source") String Source
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret
            , @Query("language") String language);
    /**
     * 接受呼救
     */
    @GET("userHelp/acceptlHelp")
    Call<ResponseBody> acceptlHelp(@Query("userId") String userId
            , @Query("reciveX") String reciveX
            , @Query("reciveY") String reciveY
            , @Query("Source") String Source
            , @Query("helpId") String helpId
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret
            , @Query("language") String language);
    /**
     * 取消呼救
     */
    @GET("userHelp/cancelHelp")
    Call<ResponseBody> cancelHelp(@Query("helpId") String helpId
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret
            , @Query("language") String language);

    /**
     * 放弃救援
     */
    @GET("userHelp/giveUpHelp")
    Call<ResponseBody> giveUpHelp(@Query("helpId") String helpId
            , @Query("userId") String userId
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret
            , @Query("language") String language);

}
