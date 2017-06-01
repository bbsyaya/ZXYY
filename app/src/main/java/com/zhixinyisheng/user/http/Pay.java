package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 支付
 * Created by 焕焕 on 2017/1/4.
 */
public interface Pay {
    /**
     * 订单详情
     */
    @GET("myUser/billDetail")
    Call<ResponseBody> billDetail(@Query("zxys_userName") String user_phone,
                                @Query("zxys_encrypt") String zxys_encrypt,
                                @Query("userId") String userId,
                                @Query("incomePayRecordId") long incomePayRecordId,
                                @Query("type") String type);


    /**
     * 申请提现
     */
    @GET("alipay-getMoney/getMoney")
    Call<ResponseBody> getMoney(@Query("zxys_userName") String user_phone,
                                @Query("zxys_encrypt") String zxys_encrypt,
                                @Query("userId") String userId,
                                @Query("alipay") String alipay,
                                @Query("money") String money,
                                @Query("realName") String realName);

    /**
     * 我的收入
     */
    @GET("myUser/myIncome")
    Call<ResponseBody> myIncome(@Query("zxys_userName") String user_phone,
                                    @Query("zxys_encrypt") String zxys_encrypt,
                                    @Query("userId") String userId);

    /**
     * 支付
     */
    @FormUrlEncoded
    @POST("pay/toPay")
    Call<ResponseBody> toPay(@Field("zxys_userName") String user_phone,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("fromUserId") String fromUserId,
                                    @Field("total_amount") String total_amount,
                                    @Field("toUserId") String toUserId,
                                    @Field("type") String type,
                                    @Field("times") String times);
    /**
     * 收入明细
     */
    @GET("myUser/searchIncome")
    Call<ResponseBody> searchIncome(@Query("zxys_userName") String user_phone,
                                  @Query("zxys_encrypt") String zxys_encrypt,
                                  @Query("userId") String userId,
                                  @Query("showCount") int showCount,
                                  @Query("currentPage") int currentPage);
    /**
     * 支出明细
     */
    @GET("myUser/searchPay")
    Call<ResponseBody> searchPay(@Query("zxys_userName") String user_phone,
                                    @Query("zxys_encrypt") String zxys_encrypt,
                                    @Query("userId") String userId,
                                    @Query("showCount") int showCount,
                                    @Query("currentPage") int currentPage);


    /**
     * 医生保存或更新个性化收费
     */
    @FormUrlEncoded
    @POST("myUser/doctorRule")
    Call<ResponseBody> doctorRule(@Field("zxys_userName") String user_phone,
                             @Field("zxys_encrypt") String zxys_encrypt,
                             @Field("userId") String userId,
                             @Field("dayMoney") String dayMoney,
                             @Field("weekMoney") String weekMoney,
                             @Field("monthMoney") String monthMoney,
                             @Field("yearMoney") String yearMoney,
                             @Field("messageCount") String messageCount);

    /**
     * 医生查询个性化收费
     */
    @GET("myUser/searchMoneyRule")
    Call<ResponseBody> searchMoneyRule(@Query("userId") String userId
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);
    /**
     * 生成订单接口  （当付费为0时）
     */
    @FormUrlEncoded
    @POST("myUser/zeroRecord")
    Call<ResponseBody> zeroRecord(@Field("zxys_userName") String user_phone,
                                  @Field("zxys_encrypt") String zxys_encrypt,
                                  @Field("userId") String userId,
                                  @Field("toUserId") String toUserId,
                                  @Field("type") String type);

}
