package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 医生模块的地址
 * Created by 焕焕 on 2016/12/31.
 */
public interface DoctorUrl {
    /**
     * 查询科室
     */
    @GET("myUser/sectionList")
    Call<ResponseBody> sectionList(@Query("zxys_userName") String user_phone,
                                   @Query("zxys_encrypt") String zxys_encrypt);


    /**
     * 查询医院
     */
    @GET("myUser/searchHospital")
    Call<ResponseBody> searchHospital(@Query("zxys_userName") String user_phone,
                                      @Query("zxys_encrypt") String zxys_encrypt,
                                      @Query("province") String province,
                                      @Query("city") String city);


    /**
     * 医生完善个人信息
     */
    @FormUrlEncoded
    @POST("myUser/doctorInfo")
    Call<ResponseBody> doctorInfo(@Field("zxys_userName") String phone,
                                  @Field("zxys_encrypt") String secret,
                                  @Field("userId") String userId,
                                  @Field("name") String name,
                                  @Field("doctorPhone") String doctorPhone,
                                  @Field("province") String province,
                                  @Field("city") String city,
                                  @Field("hospitalId") String hospitalId,
                                  @Field("hospital") String hospital,
                                  @Field("sectionId") String sectionId,
                                  @Field("section") String section,
                                  @Field("job") String job,
                                  @Field("grade") int grade,
                                  @Field("disease") String disease,
                                  @Field("cardPic") String cardPic,
                                  @Field("webchat") String webchat,
                                  @Field("qq") String qq,
                                  @Field("hospitalAddress") String hospitalAddress,
                                  @Field("username") String username,
                                  @Field("sex") String sex,
                                  @Field("age") String age,
                                  @Field("remark") String remark,
                                  @Field("headUrl") String headUrl);
}
