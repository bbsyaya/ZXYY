package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 根据用户ID查询有数据的日期
 * Created by 焕焕 on 2016/11/23.
 */
public interface FindByPid {
    /**
     * 根据用户ID查询有肺活量数据的日期
     */
    @GET("vitalCapacity/findByPid")
    Call<ResponseBody> vitalCapacityFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 血常规
     */
    @GET("cbc/findByPid")
    Call<ResponseBody> cbcFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 尿常规
     */
    @GET("urinalysis/findByPid")
    Call<ResponseBody> urinalysisFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 尿蛋白
     */
    @GET("urineprotein/findByPid")
    Call<ResponseBody> urineproteinFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 血压
     */
    @GET("blood/findByPid")
    Call<ResponseBody> bloodFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 血糖
     */
    @GET("bloodsugar/findByPid")
    Call<ResponseBody> bloodsugarFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 体温
     */
    @GET("AnimalHeat/findByPid")
    Call<ResponseBody> animalheatFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 步数
     */
    @GET("stepnumber/findByPid")
    Call<ResponseBody> stepnumberFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 睡眠质量
     */
    @GET("sleep/findByPid")
    Call<ResponseBody> sleepFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * bmi
     */
    @GET("bmi/findByPid")
    Call<ResponseBody> bmiFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 凝血
     */
    @GET("cruor/findByPid")
    Call<ResponseBody> cruorFindByPid(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 乙肝五项
     */
    @GET("hbv/findByPid")
    Call<ResponseBody> hbv(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 心率
     */
    @GET("heartrate/findByid")
    Call<ResponseBody> heartrate(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 免疫补体
     */
    @GET("immune/findByPid")
    Call<ResponseBody> immune(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 肾脏彩超
     */
    @GET("kidneycte/findByPid")
    Call<ResponseBody> kidneycte(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 肝功能
     */
    @GET("liverf/findByPid")
    Call<ResponseBody> liverf(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 肾功能
     */
    @GET("renalf/findByPid")
    Call<ResponseBody> renalf(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 病毒检测
     */
    @GET("virus/findByPid")
    Call<ResponseBody> virus(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     *抗体系列
     */
    @GET("antibody/findByPid")
    Call<ResponseBody> antibody(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     *电解质
     */
    @GET("electrolyte/findByPid")
    Call<ResponseBody> electrolyte(@Query("USER_ID") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);






}
