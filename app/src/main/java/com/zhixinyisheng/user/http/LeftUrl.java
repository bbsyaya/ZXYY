package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 左侧接口
 * Created by gjj on 2016/10/26.
 */
public interface LeftUrl {

    /**
     * 问题反馈
     */
    @POST("feedback/save")
    @FormUrlEncoded
    Call<ResponseBody> feedback (@Field("zxys_userName")String phone, @Field("zxys_encrypt") String encrypt, @Field("userId") String userID, @Field("content") String content);

    /**
     * 查看个人资料
     */
    @GET("myUser/selInfro")
    Call<ResponseBody> lookperson(@Query("userId") String userid);

    /**
     * 编辑个人信息
     */
    @FormUrlEncoded
    @POST("myUser/editInfro")
    Call<ResponseBody> personinformation(@Field("zxys_userName") String phone,@Field("zxys_encrypt") String encrypt,
                                         @Field("phone") String phone2,@Field("headUrl") String headUrl,
                                         @Field("username") String name,@Field("sex") String sex,
                                         @Field("age") String age,@Field("address") String address,
                                         @Field("remark") String remark,
                                         @Field("userId") String userId,
                                         @Field("country") String country);
    /**
     * 医生认证
     */
    @FormUrlEncoded
    @POST("myUser/doctor")
    Call<ResponseBody> renZheng(@Field("zxys_userName") String phone,@Field("zxys_encrypt") String encrypt,
                                         @Field("userId") String userId,@Field("attp") String attp,
                                         @Field("attpUrl") String attpUrl);

    /**
     * 查看我是医生认证审核状态
     */
    @GET("myUser/doctorNews")
    Call<ResponseBody> doctorNews(@Query("userId") String USER_ID
            ,@Query("zxys_userName") String phone
            ,@Query("zxys_encrypt") String secret);
}
