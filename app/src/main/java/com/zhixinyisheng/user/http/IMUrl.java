package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/19  15:22
 * <p/>
 * 类说明:
 */
public interface IMUrl {
    /**
     * 查看聊天信息
     */
    @GET("friends/chatNumber")
    Call<ResponseBody> chatInfo(@Query("zxys_userName") String user_phone,
                                @Query("zxys_encrypt") String zxys_encrypt,
                                @Query("loginUserId") String loginUserId,
                                @Query("friendsId") String friendsId);


    /**
     * 好友列表
     */
    @FormUrlEncoded
    @POST("friends/list")
    Call<ResponseBody> getMyFriends(@Field("zxys_userName") String user_phone,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("fromuserid") String fromuserid);


    /**
     * 搜索好友(根据手机号)
     */
    @FormUrlEncoded
    @POST("friends/findFrinedsByPhone")
    Call<ResponseBody> findFrined(@Field("zxys_userName") String user_phone,
                                  @Field("zxys_encrypt") String zxys_encrypt,
                                  @Field("fromuserid") String fromuserid,
                                  @Field("phone") String phone,
                                  @Field("searchContent") String searchContent,
                                  @Field("language") String language);
//    /**
//     * 搜索好友(根据邮箱)
//     */
//    @FormUrlEncoded
//    @POST("friends/findFrinedsByPhone")
//    Call<ResponseBody> findFrinedE(@Field("zxys_userName") String user_phone,
//                                  @Field("zxys_encrypt") String zxys_encrypt,
//                                  @Field("fromuserid") String fromuserid,
//                                  @Field("email") String email,
//                                  @Field("language") String language);

    /**
     * 添加好友
     */
    @FormUrlEncoded
    @POST("friends/addFrineds")
    Call<ResponseBody> addFrined(@Field("zxys_userName") String user_phone,
                                 @Field("zxys_encrypt") String zxys_encrypt,
                                 @Field("fromuserid") String fromuserid,
                                 @Field("touserid") String touserid,
                                 @Field("language") String language);


    /**
     * 添加好友
     */
    @FormUrlEncoded
    @POST("friends/addFrineds")
    Call<ResponseBody> addFrined(@Field("zxys_userName") String user_phone,
                                 @Field("zxys_encrypt") String zxys_encrypt,
                                 @Field("fromuserid") String fromuserid,
                                 @Field("touserid") String touserid,
                                 @Field("frinedsRemark") String frinedsRemark,
                                 @Field("language") String language);

    /**
     * 修改好友备注
     */
    @FormUrlEncoded
    @POST("friends/editFriendRemark")
    Call<ResponseBody> editFriendRemark(@Field("zxys_userName") String user_phone,
                                        @Field("zxys_encrypt") String zxys_encrypt,
                                        @Field("fromuserid") String fromuserid,
                                        @Field("touserid") String touserid,
                                        @Field("frinedsRemark") String frinedsRemark);


    /**
     * 删除好友
     */
    @FormUrlEncoded
    @POST("friends/delete")
    Call<ResponseBody> deleteFrined(@Field("zxys_userName") String user_phone,
                                    @Field("zxys_encrypt") String zxys_encrypt,
                                    @Field("fromuserid") String fromuserid,
                                    @Field("touserid") String touserid);

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("sysMessage/noReadMessage")
    Call<ResponseBody> getsysMessage(@Field("zxys_userName") String user_phone,
                                     @Field("zxys_encrypt") String zxys_encrypt,
                                     @Field("fromuserid") String fromuserid);

    /**
     * 好友详情
     */
    @GET("friends/friendsDetail")
    Call<ResponseBody> getfriendsDetail(@Query("zxys_userName") String user_phone,
                                        @Query("zxys_encrypt") String zxys_encrypt,
                                        @Query("userId") String userId,
                                        @Query("touserid") String touserid,
                                        @Query("language") String language);

    /**
     * 个人详情
     */
    @GET("myUser/selInfro")
    Call<ResponseBody> getMyinfo(@Query("zxys_userName") String user_phone,
                                 @Query("zxys_encrypt") String zxys_encrypt,
                                 @Query("userId") String userId);

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("sysMessage/deletes")
    Call<ResponseBody> deletesysMessage(@Field("zxys_userName") String user_phone,
                                        @Field("zxys_encrypt") String zxys_encrypt,
                                        @Field("userSystemIds") String userSystemIds);


    /**
     * SOS发送对象设置
     */
    @FormUrlEncoded
    @POST("userSystem/SosSet")
    Call<ResponseBody> sosSet(@Field("zxys_userName") String user_phone,
                              @Field("zxys_encrypt") String zxys_encrypt,
                              @Field("userId") String userId,
                              @Field("sos") String sos);

    /**
     * SOS发送对象设置
     */
    @FormUrlEncoded
    @POST("userSystem/seeSet")
    Call<ResponseBody> seeSet(@Field("zxys_userName") String user_phone,
                              @Field("zxys_encrypt") String zxys_encrypt,
                              @Field("userId") String userId,
                              @Field("myData") String sos);


    /**
     * 查看系统消息详情
     */
    @FormUrlEncoded
    @POST("sysMessage/findById")
    Call<ResponseBody> getsysMessageDetail(@Field("zxys_userName") String user_phone,
                                           @Field("zxys_encrypt") String zxys_encrypt,
                                           @Field("sysMessageId") String sysMessageId);

    /**
     * 更新消息状态为已读
     */
    @FormUrlEncoded
    @POST("sysMessage/readSet")
    Call<ResponseBody> readSet(@Field("zxys_userName") String user_phone,
                               @Field("zxys_encrypt") String zxys_encrypt,
                               @Field("sysMessageId") String sysMessageId);


    /**
     * 添加好友至我关注列表
     */
    @FormUrlEncoded
    @POST("friends/careList")
    Call<ResponseBody> careList(@Field("zxys_userName") String user_phone,
                                @Field("zxys_encrypt") String zxys_encrypt,
                                @Field("fromuserid") String fromuserid);


    /**
     * 添加好友至我关注列表
     */
    @FormUrlEncoded
    @POST("friends/addtoCareFrineds")
    Call<ResponseBody> addtoCareFrineds(@Field("zxys_userName") String user_phone,
                                        @Field("zxys_encrypt") String zxys_encrypt,
                                        @Field("fromuserid") String fromuserid,
                                        @Field("touserid") String touserid);

    /**
     * 批量添加好友至我关注列表
     */
    @FormUrlEncoded
    @POST("friends/addAllToCareFrineds")
    Call<ResponseBody> addAllToCareFrineds(@Field("zxys_userName") String user_phone,
                                           @Field("zxys_encrypt") String zxys_encrypt,
                                           @Field("fromuserid") String fromuserid,
                                           @Field("touserid") String touserid);


    /**
     * 将好友从关注列表移除
     */
    @FormUrlEncoded
    @POST("friends/delFromCareFrineds")
    Call<ResponseBody> delFromCareFrineds(@Field("zxys_userName") String user_phone,
                                          @Field("zxys_encrypt") String zxys_encrypt,
                                          @Field("fromuserid") String fromuserid,
                                          @Field("touserid") String touserid);
}
