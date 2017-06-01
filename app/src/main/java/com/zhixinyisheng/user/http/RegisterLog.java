package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 发送验证码
 */
public interface RegisterLog {

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("sbyypostphone/postPhoneHandle")
    Call<ResponseBody> sendVerify(@Field("phone") String phone, @Field("psan") String psan,@Field("actId") int actId);

    /**
     * 验证验证码的
     */
    @FormUrlEncoded
    @POST("sbyypostphone/postPhoneHandle")
    Call<ResponseBody> yanzhengVerify(@Field("phone") String phone, @Field("psan") String psan,@Field("actId") int actId,@Field("sign") String sign);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("myUser/register")
    Call<ResponseBody> register(
            @Field("phone") String phone,
            @Field("source") String source,
            @Field("password") String password,
            @Field("actId") int actId,
            @Field("sign") String sign,
            @Field("language") String language);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("myUser/register")
    Call<ResponseBody> registerE(
            @Field("email") String email,
            @Field("source") String source,
            @Field("password") String password,
            @Field("actId") int actId,
            @Field("sign") String sign,
            @Field("language") String language);


    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("myUser/wangPass")
    Call<ResponseBody> resetPassword(
            @Field("phone") String account,
            @Field("content") String content,
            @Field("password") String password,
            @Field("actId") int actId,
            @Field("sign") String sign,
            @Field("language") String language);
//    /**
//     * 忘记密码
//     */
//    @FormUrlEncoded
//    @POST("myUser/wangPass")
//    Call<ResponseBody> resetPasswordE(
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("actId") int actId,
//            @Field("sign") String sign,
//            @Field("language") String language);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("myUser/login")
    Call<ResponseBody> Login(@Field("phone") String PHONE,
                             @Field("content") String content,
                             @Field("password") String PASSWORD,
                             @Field("SOURCE") String SOURCE,
                             @Field("UID") String UID,
                             @Field("registrationId") String registrationId,
                             @Field("language") String langguage);
//    /**
//     * 登录
//     */
//    @FormUrlEncoded
//    @POST("myUser/login")
//    Call<ResponseBody> LoginE(@Field("email") String email,
//                             @Field("password") String PASSWORD,
//                             @Field("SOURCE") String SOURCE,
//                             @Field("UID") String UID,
//                             @Field("registrationId") String registrationId,
//                             @Field("language") String langguage);


    @POST("RegisterLog/threeLogin")
    @FormUrlEncoded
    Call<ResponseBody> threeLogin(@Field(("openid")) String openid);

    /**
     * 根据手机号查询该用户是否存在
     */
    @GET("myUser/phoneNull")
    Call<ResponseBody> phoneNull(@Query("phone") String phone);

    /**
     * 发送邮箱验证码接口
     */
    @GET("email/sendEmail")
    Call<ResponseBody> sendEmail(@Query("postToEmail") String postToEmail,
                                 @Query("psan") String psan,
                                 @Query("actId") int actId);

}
