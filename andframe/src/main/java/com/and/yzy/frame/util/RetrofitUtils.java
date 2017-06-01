package com.and.yzy.frame.util;

import com.and.yzy.frame.config.HttpConfig;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/4/11.
 */
public class RetrofitUtils {
    private static Retrofit singleton;

    public static <T> T createApi(Class<T> clazz) {

        return singleton.create(clazz);
    }

    public static Retrofit getInstance() {


        return singleton;
    }

    public static void init() {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .baseUrl(HttpConfig.BASE_URL)
                            .client(OkHttpUtils.getInstance())
                            .build();
                }
            }
        }

    }


}
