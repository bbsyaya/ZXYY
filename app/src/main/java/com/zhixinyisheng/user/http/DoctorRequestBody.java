package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Yuanyx on 2017/1/4.
 */

public interface DoctorRequestBody {
    //用户分页查询医生
    @POST("myUser/searchDoctor")
    @FormUrlEncoded
    Call<ResponseBody> searchDoctor(
            @Field("zxys_userName") String zxys_userName,
            @Field("zxys_encrypt") String zxys_encrypt,
            @Field("userId") String userId,
            @Field("showCount") String showCount,
            @Field("currentPage") String currentPage,
            @Field("keyword") String keyword,
            @Field("province") String province,
            @Field("city") String city,
            @Field("section") String section,
            @Field("job") String job,
            @Field("locaProvince") String locaProvince,
            @Field("locaCity") String locaCity
    );

    //推荐医生
    @GET("myUser/recommendDoctor")
    Call<ResponseBody> recommendDoctor(@Query("zxys_userName") String zxys_userName,
                                       @Query("zxys_encrypt") String zxys_encrypt,
                                       @Query("userId") String userId,
                                       @Query("showCount") String showCount,
                                       @Query("currentPage") String currentPage,
                                       @Query("locaProvince") String locaProvince,
                                       @Query("locaCity") String locaCity,
                                       @Query("doctorId") String doctorId);

    //医生服务列表
    @GET("myUser/myRecordList")
    Call<ResponseBody> serviceList(@Query("zxys_userName") String zxys_userName,
                                   @Query("zxys_encrypt") String zxys_encrypt,
                                   @Query("userId") String userId);

    //评价医生查询接口
    @GET("myUser/seeAppraise")
    Call<ResponseBody> evaluateDoctorSelect(@Query("zxys_userName") String zxys_userName,
                                            @Query("zxys_encrypt") String zxys_encrypt,
                                            @Query("payRecordId") String payRecordId);

    //评价医生
    @POST("myUser/appraise")
    @FormUrlEncoded
    Call<ResponseBody> evaluateDoctor(@Field("zxys_userName") String zxys_userName,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("userId") String userId,
                                      @Field("toUserId") String toUserId,
                                      @Field("payRecordId") String payRecordId,
                                      @Field("type") String type);

    //查询科室列表
    @GET("myUser/sectionList")
    Call<ResponseBody> sectionList(@Query("zxys_userName") String zxys_userName,
                                   @Query("zxys_encrypt") String zxys_encrypt);

    //查看医生首页
    @GET("myUser/seeDoctorHomePage")
    Call<ResponseBody> doctorInfo(@Query("zxys_userName") String zxys_userName,
                                  @Query("zxys_encrypt") String zxys_encrypt,
                                  @Query("userId") String userId,
                                  @Query("doctorId") String doctorId);

    //举报医生
    @POST("myUser/inform")
    @FormUrlEncoded
    Call<ResponseBody> appealDoctor(@Field("zxys_userName") String zxys_userName,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("fromuserid") String fromuserid,
                                    @Field("touserid") String touserid,
                                    @Field("content") String content,
                                    @Field("picUrl") String picUrl);

    //医生发布简介(包括修改)
    @POST("myUser/saveIntro")
    @FormUrlEncoded
    Call<ResponseBody> introduceApi(@Field("zxys_userName") String zxys_userName,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("userId") String userId,
                                    @Field("intro") String intro);

    //医生发布荣誉(包括修改)
    @POST("myUser/saveIntro")
    @FormUrlEncoded
    Call<ResponseBody> honorApi(@Field("zxys_userName") String zxys_userName,
                                @Field("zxys_encrypt") String zxys_encrypt,
                                @Field("userId") String userId,
                                @Field("honor") String honor);

    //分页查询医生所有动态
    @GET("myUser/doctorNewsList")
    Call<ResponseBody> doctorDynamic(
            @Query("zxys_userName") String zxys_userName,
            @Query("zxys_encrypt") String zxys_encrypt,
            @Query("userId") String userId,
            @Query("toUserId") String toUserID,
            @Query("showCount") String showCount,
            @Query("currentPage") String currentPage);

    //医生查看详细动态
    @POST("myUser/seeOneNews")
    @FormUrlEncoded
    Call<ResponseBody> doctorDynamicDetail(@Field("zxys_userName") String zxys_userName,
                                           @Field("zxys_encrypt") String zxys_encrypt,
                                           @Field("userId") String userId,
                                           @Field("doctorsInfoId") String doctorsInfoId);

    //医生发布动态
    @POST("myUser/saveInfo")
    @FormUrlEncoded
    Call<ResponseBody> publishDynamic(@Field("zxys_userName") String zxys_userName,
                                      @Field("zxys_encrypt") String zxys_encrypt,
                                      @Field("userId") String userId,
                                      @Field("content") String content,
                                      @Field("picUrl") String picUrl);


    //医生动态点赞(取消点赞)
    @GET("myUser/newsZan")
    Call<ResponseBody> clickPraise(
            @Query("zxys_userName") String zxys_userName,
            @Query("zxys_encrypt") String zxys_encrypt,
            @Query("zanUserId") String zanUserId,//zanUserId*点赞人用户ID
            @Query("userId") String userId,//userId*发表文章人ID
            @Query("doctorsInfoId") String doctorsInfoId);

    //医生删除动态接口
    @POST("myUser/deleteNews")
    @FormUrlEncoded
    Call<ResponseBody> deleteDynamic(@Field("zxys_userName") String zxys_userName,
                                     @Field("zxys_encrypt") String zxys_encrypt,
                                     @Field("userId") String userId,
                                     @Field("doctorsInfoId") String doctorsInfoId
    );
    //删除我的服务
    @POST("myUser/deleteOrder")
    @FormUrlEncoded
    Call<ResponseBody> deleteOrder(@Field("zxys_userName") String zxys_userName,
                                   @Field("zxys_encrypt") String zxys_encrypt,
                                   @Field("payRecordId") String payRecordId);

    //生成订单
    @POST("myUser/freeRecord")
    @FormUrlEncoded
    Call<ResponseBody> createOrder(@Field("zxys_userName") String zxys_userName,
                                   @Field("zxys_encrypt") String zxys_encrypt,
                                   @Field("userId") String userId,
                                   @Field("toUserId") String toUserId);

    //聊天信息
    @POST("myUser/chatSave")
    @FormUrlEncoded
    Call<ResponseBody> uploadingChatRecord(@Field("zxys_userName") String zxys_userName,
                                           @Field("zxys_encrypt") String zxys_encrypt,
                                           @Field("userId") String userId,
                                           @Field("chatContent") String chatContent);

    //得到当前聊天的条数
    @GET("friends/chatNumber")
    Call<ResponseBody> getChatNumber(@Query("zxys_userName") String zxys_userName,
                                     @Query("zxys_encrypt") String zxys_encrypt,
                                     @Query("loginUserId") String loginUserId,
                                     @Query("friendsId") String friendsId);

}
