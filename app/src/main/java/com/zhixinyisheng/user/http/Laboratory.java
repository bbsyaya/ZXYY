package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 焕焕 on 2017/2/22.
 */
public interface Laboratory {
    /**
     * 图片返回文字
     */
    @FormUrlEncoded
    @POST("laboratory/showPic")
    Call<ResponseBody> showPic(@Field("zxys_userName") String user_phone,
                            @Field("zxys_encrypt") String zxys_encrypt,
                            @Field("pic1") String userId,
                            @Field("pic2") String content,
                            @Field("pic3") String byTime);
    /**
     * 保存
     */
    @FormUrlEncoded
    @POST("laboratory/save")
    Call<ResponseBody> save(@Field("zxys_userName") String user_phone,
                            @Field("zxys_encrypt") String zxys_encrypt,
                            @Field("userId") String userId,
                            @Field("content") String content,
                            @Field("byTime") String byTime);

    /**
     * 444
     */
    @GET("laboratory/list")
    Call<ResponseBody> list(@Query("userId") String userId
            , @Query("showCount") String showCount
            , @Query("currentPage") String currentPage
            , @Query("byTime") String byTime
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 查询有数据的日期
     */
    @GET("laboratory/findByPid")
    Call<ResponseBody> findByPid(@Query("userId") String userId
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 重要数据入库(尿常规)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave1(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("PROPORTION") String PROPORTION,
                                      @Field("PROTEIN") String PROTEIN,
                                      @Field("BLD") String BLD,
                                      @Field("language") String language);

    /**
     * 重要数据入库(血常规)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave2(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("OXYPHORASE") String OXYPHORASE,
                                      @Field("language") String language);

    /**
     * 重要数据入库(尿蛋白定量)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave3(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("UPVALUE") String UPVALUE,
                                      @Field("UPVALUE_UNIT") String UPVALUE_UNIT,
                                      @Field("language") String language);

    /**
     * 重要数据入库(肾功能)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave4(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("CREATININE") String CREATININE,
                                      @Field("CREATININE_UNIT") String CREATININE_UNIT,
                                      @Field("UREANITROGEN") String UREANITROGEN,
                                      @Field("UREANITROGEN_UNIT") String UREANITROGEN_UNIT,
                                      @Field("language") String language);

    /**
     * 重要数据入库(肝功能)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave5(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("ALT") String ALT,
                                      @Field("ALT_UNIT") String ALT_UNIT,
                                      @Field("language") String language);

    /**
     * 重要数据入库(电解质)
     */
    @FormUrlEncoded
    @POST("laboratory/importantSave")
    Call<ResponseBody> importantSave6(@Field("zxys_userName") String user_phone,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("USER_ID") String USER_ID,
                                      @Field("type") int type,
                                      @Field("sex") int sex,
                                      @Field("potassium") String potassium,
                                      @Field("potassium_UNIT") String potassium_UNIT,
                                      @Field("calcium") String calcium,
                                      @Field("calcium_UNIT") String calcium_UNIT,
                                      @Field("phosphorus") String phosphorus,
                                      @Field("phosphorus_UNIT") String phosphorus_UNIT,
                                      @Field("language") String language);

}
