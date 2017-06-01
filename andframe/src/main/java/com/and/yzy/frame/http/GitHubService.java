package com.and.yzy.frame.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 *
 */
public interface GitHubService {
    @POST("sort.aspx")
    Call<ResponseBody> sort();
}
