package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 日历
 * Created by gjj on 2016/11/7.
 */
public interface DataRili {
    /**
     * 尿蛋白定量日历
     */
    @GET("urineprotein/findBytime")
    Call<ResponseBody> ndbrili(@Query("zxys_userName")String username,@Query("zxys_encrypt")String zxys_encrypt,@Query("USER_ID")String userid, @Query("searchDate") String searchdate);
}
