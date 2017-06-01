package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 焕焕 on 2017/4/19.
 */

public interface Ranking {
    /**
     * 步数排行榜
     */
    @GET("stepnumber/stepSort")
    Call<ResponseBody> stepSort(@Query("userId") String userId
            , @Query("showCount") int showCount
            , @Query("currentPage") int currentPage
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 步数排行榜
     */
    @GET("vitalCapacity/vitalCapacitySort")
    Call<ResponseBody> vitalCapacitySort(@Query("userId") String userId
            , @Query("showCount") int showCount
            , @Query("currentPage") int currentPage
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
}
