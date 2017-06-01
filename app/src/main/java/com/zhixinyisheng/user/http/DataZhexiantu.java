package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 折线图
 * Created by gjj on 2016/11/7.
 */
public interface DataZhexiantu {
    /**尿常规*/
    @GET("urinalysis/cellLineChart")
    Call<ResponseBody> ncg_zhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id,@Query("byTime")String string, @Query("PARAMS") String PARAMS);

    /**尿蛋白定量*/
    @GET("urineprotein/cellLineChart")
    Call<ResponseBody> ndbZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id,@Query("byTime")String string, @Query("PARAMS") String PARAMS);

    /**血常规*/
    @GET("cbc/cellLineChart")
    Call<ResponseBody> xcgZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id,@Query("byTime")String string, @Query("PARAMS") String PARAMS);


    /**免疫球蛋白*/
    @GET("immune/cellLineChart")
    Call<ResponseBody> myqdbZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id,@Query("byTime")String string, @Query("PARAMS") String PARAMS);

    /** 肾功能*/
    @GET("renalf/cellLineChart")
    Call<ResponseBody> sgnZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id, @Query("byTime") String string, @Query("PARAMS") String PARAMS);

    /** 肝功能*/
    @GET("liverf/cellLineChart")
    Call<ResponseBody> ggnZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id, @Query("byTime") String string, @Query("PARAMS") String PARAMS);

    /** 电解质*/
    @GET("electrolyte/cellLineChart")
    Call<ResponseBody> djzZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id, @Query("byTime") String string, @Query("PARAMS") String PARAMS);

    /** 肾脏彩超*/
    @GET("kidneycte/cellLineChart")
    Call<ResponseBody> szccZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                    @Query("USER_ID") String user_id, @Query("byTime") String string, @Query("PARAMS") String PARAMS);

    /** 凝血*/
    @GET("cruor/cellLineChart")
    Call<ResponseBody> nxjcZhexiantu(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                     @Query("USER_ID") String user_id, @Query("byTime") String string, @Query("PARAMS") String PARAMS);
}
