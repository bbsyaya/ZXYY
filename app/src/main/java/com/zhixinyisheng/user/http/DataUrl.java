package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 数据的提交
 * Created by 焕焕 on 2016/10/27.
 */
public interface DataUrl {
    /**
     * 更新语言
     */
    @FormUrlEncoded
    @POST("myUser/autoLogin")
    Call<ResponseBody> editLanguage(@Field("userId") String userId
            , @Field("language") String language
            , @Field("SOURCE") String SOURCE
            , @Field("UID") String UID
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);


    /**
     * 根据当天日期查询舌诊数据
     */
    @GET("vitalCapacity/lineChartList")
    Call<ResponseBody> checkPlumonaryZXT(@Query("USER_ID") String USER_ID
            , @Query("BEGINDATE") String BEGINDATE
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);


    /**
     * 根据当天日期查询舌诊数据
     */
    @GET("vitalCapacity/findBytime")
    Call<ResponseBody> vitalCapacityFindBytime(@Query("USER_ID") String USER_ID
            , @Query("searchDate") String searchDate
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 肺活量数据添加
     */
    @FormUrlEncoded
    @POST("vitalCapacity/save")
    Call<ResponseBody> addPulmonary(@Field("USER_ID") String USER_ID
            , @Field("NUM") String num
            , @Field("BYTIME") String BYTIME
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);


    /**
     * 心率数据添加
     */
    @FormUrlEncoded
    @POST("heartrate/save")
    Call<ResponseBody> addXinLv(@Field("USER_ID") String USER_ID
            , @Field("num") String num
            , @Field("BYTIME") String BYTIME
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);

    /**
     * 心率折线图数据查询
     */
    @GET("heartrate/lineChartList")
    Call<ResponseBody> checkXinLvZXT(@Query("USER_ID") String USER_ID
            , @Query("BEGINDATE") String BEGINDATE
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 分页查询心率所有数据
     */
    @GET("heartrate/list")
    Call<ResponseBody> checkXinLvData(@Query("USER_ID") String USER_ID
            , @Query("showCount") int showCount
            , @Query("currentPage") int currentPage
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 血压折线图数据查询
     */
    @GET("blood/lineChartList")
    Call<ResponseBody> checkXueYaZXT(@Query("USER_ID") String USER_ID
            , @Query("BEGINDATE") String BEGINDATE
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 血压折线图数据查询
     */
    @GET("blood/allList")
    Call<ResponseBody> BloodAllList(@Query("USER_ID") String USER_ID
            , @Query("showCount") int showCount
            , @Query("currentPage") int currentPage
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);


    /**
     * 血压数据添加
     */
    @FormUrlEncoded
    @POST("blood/save")
    Call<ResponseBody> addXueYa(@Field("USER_ID") String USER_ID
            , @Field("HIGHPRESSURE") String HIGHPRESSURE
            , @Field("LOWPRESSURE") String LOWPRESSURE
            , @Field("BYTIME") String BYTIME
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret
            , @Field("language") String language);

    /**
     * 步数数据添加
     */
    @FormUrlEncoded
    @POST("stepnumber/save")
    Call<ResponseBody> addBuShu(@Field("USER_ID") String USER_ID
            , @Field("STEPS") String STEPS
            , @Field("BYTIME") String BYTIME
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);

    /**
     * 血压折线图数据查询
     */
    @GET("stepnumber/lineChartList")
    Call<ResponseBody> checkBuShuZXT(@Query("USER_ID") String USER_ID
            , @Query("BEGINDATE") String BEGINDATE
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 睡眠质量数据添加
     */
    @FormUrlEncoded
    @POST("sleep/save")
    Call<ResponseBody> addShuiMian(@Field("USER_ID") String USER_ID
            , @Field("SLEEP") String SLEEP
            , @Field("HOUR") String HOUR
            , @Field("BYTIME") String BYTIME
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);

    /**
     * 睡眠质量折线图数据查询
     */
    @GET("sleep/cellLineChart")
    Call<ResponseBody> checkShuiMianZXT(@Query("USER_ID") String USER_ID
            , @Query("BEGINDATE") String BEGINDATE
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * BMI数据添加
     */
    @FormUrlEncoded
    @POST("bmi/save")
    Call<ResponseBody> addBMI(@Field("USER_ID") String USER_ID
            , @Field("num") String num
            , @Field("rangeValue") String rangeValue
            , @Field("byTime") String byTime
            , @Field("colorFlag") String colorFlag
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret);

    /**
     * BMI折线图数据查询
     */
    @GET("bmi/lineChartList")
    Call<ResponseBody> checkBMIZXT(@Query("USER_ID") String USER_ID
            , @Query("byTime") String byTime
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 体温数据添加
     */
    @FormUrlEncoded
    @POST("AnimalHeat/save")
    Call<ResponseBody> addTiWen(@Field("USER_ID") String USER_ID
            , @Field("DEGREE") String DEGREE
            , @Field("BYTIME") String byTime
            , @Field("TIMELIMIT") String TIMELIMIT
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret
            , @Field("language") String language);

    /**
     * 体温折线图数据查询
     */
    @GET("AnimalHeat/lineChartList")
    Call<ResponseBody> checkTiWenZXT(@Query("USER_ID") String USER_ID
            , @Query("byTime") String byTime
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 分页查询体温所有数据
     */
    @GET("AnimalHeat/allList")
    Call<ResponseBody> temperatureAllList(@Query("USER_ID") String USER_ID
            , @Query("showCount") int showCount
            , @Query("currentPage") int currentPage
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 根据日期查询之前30天的数据(体温)
     */
    @GET("AnimalHeat/timeBeforeList")
    Call<ResponseBody> temperatureTimeBeforeList(@Query("USER_ID") String USER_ID
            , @Query("BYTIME") String BYTIME
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 根据日期查询之前30天的数据(血糖)
     */
    @GET("bloodsugar/timeBeforeList")
    Call<ResponseBody> bloodSugerTimeBeforeList(@Query("USER_ID") String USER_ID
            , @Query("BYTIME") String BYTIME
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 上传图片地址
     */
    @FormUrlEncoded
    @POST("pictureinfo/addPic")
    Call<ResponseBody> addPic(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("userId") String USER_ID,
                              @Field("URL") String picurl, @Field("TYPE") String type, @Field("PSAN") String psan);

    /**
     * 血糖数据添加
     */
    @FormUrlEncoded
    @POST("bloodsugar/save")
    Call<ResponseBody> addXueTang(@Field("USER_ID") String USER_ID
            , @Field("num") String num
            , @Field("type") String type
            , @Field("rangeValue") String rangeValue
            , @Field("byTime") String byTime
            , @Field("colorFlag") String colorFlag
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret
            , @Field("language") String language);

    /**
     * 体温折线图数据查询
     */
    @GET("bloodsugar/lineChartList")
    Call<ResponseBody> checkXueTangZXT(@Query("USER_ID") String USER_ID
            , @Query("byTime") String byTime
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 查看化验单图片
     */
    @GET("pictureinfo/seePic")
    Call<ResponseBody> seePic(@Query("userId") String USER_ID
            , @Query("TYPE") String TYPE
            , @Query("ADDTIME") String ADDTIME
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 删除化验单图片
     */
    @GET("pictureinfo/delPic")
    Call<ResponseBody> delPic(@Query("userId") String USER_ID
            , @Query("URL") String URL
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 根据图片色值查询舌诊结果
     */
    @FormUrlEncoded
    @POST("tongue/save")
    Call<ResponseBody> getSheColor(@Field("rAverage") String rAverage
            , @Field("gAverage") String gAverage
            , @Field("bAverage") String bAverage
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret
            , @Field("userId") String userId
            , @Field("userPic") String userPic
            , @Field("picALL") String picALL
            , @Field("language") String language);

    /**
     * 根据当天日期查询舌诊数据
     */
    @GET("tongue/findBytime")
    Call<ResponseBody> findBytime(@Query("userId") String USER_ID
            , @Query("searchDate") String searchDate
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret
            , @Query("language") String language);

    /**
     * 根据用户ID查询舌诊创建时间
     */
    @GET("tongue/findByPid")
    Call<ResponseBody> findByPid(@Query("userId") String USER_ID
            , @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret);

    /**
     * 用户数据统计
     */
    @FormUrlEncoded
    @POST("model/modelCount")
    Call<ResponseBody> modelCount(@Field("USERID") String USERID
            , @Field("zxys_userName") String phone
            , @Field("zxys_encrypt") String secret
            , @Field("cbc") int cbc
            , @Field("urineprotein") int urineprotein
            , @Field("blood") int blood
            , @Field("bloodsugar") int bloodsugar
            , @Field("animalHeat") int animalHeat
            , @Field("vitalCapacity") int vitalCapacity
            , @Field("stepnumber") int stepnumber
            , @Field("bmi") int bmi
            , @Field("cruor") int cruor
            , @Field("hbv") int hbv
            , @Field("heartrate") int heartrate
            , @Field("immune") int immune
            , @Field("kidneycte") int kidneycte
            , @Field("liverf") int liverf
            , @Field("renalf") int renalf
            , @Field("virus") int virus
            , @Field("antibody") int antibody
            , @Field("electrolyte") int electrolyte
            , @Field("tongue") int tongue);

    /**
     * 和风天气接口
     */
    @GET("https://free-api.heweather.com/v5/weather?")
    Call<ResponseBody> weatherApi(@Query("city") String city
            , @Query("key") String key);

    @GET("http://api.openweathermap.org/data/2.5/weather")
    Call<ResponseBody> weatherEnApi(@Query("q") String q
            , @Query("appid") String appid);

    /**
     * 综合信息接口
     */
    @GET("myUser/homePageInfo")
    Call<ResponseBody> synthesizeInfo(
            @Query("zxys_userName") String phone
            , @Query("zxys_encrypt") String secret,
            @Query("userId") String userId);

    //保存用药信息
    @FormUrlEncoded
    @POST("useMedicien/save")
    Call<ResponseBody> saveUseMedicineInfo(
            @Field("zxys_userName") String phone,
            @Field("zxys_encrypt") String secret,
            @Field("fromUserId") String fromUserId,
            @Field("toUserId") String toUserId,
            @Field("medicienName") String medicienName,
            @Field("count") String count,
            @Field("alertTime") String alertTime,
            @Field("beginTime") String beginTime,
            @Field("endTime") String endTime,
            @Field("remark") String remark
    );

    //修改用药信息
    @FormUrlEncoded
    @POST("useMedicien/edit")
    Call<ResponseBody> editUseMedicineInfo(
            @Field("zxys_userName") String phone,
            @Field("zxys_encrypt") String secret,
            @Field("medicineId") String medicineId,
            @Field("medicienName") String medicienName,
            @Field("count") String count,
            @Field("alertTime") String alertTime,
            @Field("beginTime") String beginTime,
            @Field("endTime") String endTime,
            @Field("oepnVoice") String oepnVoice,
            @Field("remark") String remark
    );

    //delete用药信息
    @FormUrlEncoded
    @POST("useMedicien/del")
    Call<ResponseBody> deleteUseMedicineInfo(
            @Query("zxys_userName") String phone,
            @Query("zxys_encrypt") String secret,
            @Field("medicineId") String medicineId
    );

    //根据日期查询数据
    @GET("useMedicien/findByTime")
    Call<ResponseBody> findByTimeToData(
            @Query("zxys_userName") String phone,
            @Query("zxys_encrypt") String secret,
            @Query("toUserId") String userId,
            @Query("time") String time
    );

    //用药状态修改
    @FormUrlEncoded
    @POST("medenimeandpush/isEatEdit")
    Call<ResponseBody> amindEatState(
            @Field("zxys_userName") String phone,
            @Field("zxys_encrypt") String secret,
            @Field("medenimeAndPushId") String medenimeAndPushId,
            @Field("isEat") String isEat
    );

    //用药状态修改
    @FormUrlEncoded
    @POST("friends/isAllAlertEdit")
    Call<ResponseBody> isAlertEdit(
            @Field("zxys_userName") String phone,
            @Field("zxys_encrypt") String secret,
            @Field("fromUserId") String fromUserId,
            @Field("toUserId") String toUserId,
            @Field("isAllAlert") String isAllAlert
    );

    //查询已输入药名
    @GET("historyMedicineName/selectName")
    Call<ResponseBody> selectMedcineName(
            @Query("zxys_userName") String phone,
            @Query("zxys_encrypt") String secret,
            @Query("userId") String userId
    );

}
