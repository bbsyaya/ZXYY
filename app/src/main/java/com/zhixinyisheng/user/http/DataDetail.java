package com.zhixinyisheng.user.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 化验单具体项
 * Created by gjj on 2016/11/7.
 */
public interface DataDetail {
    /**
     * 提交尿常规数据
     */
    @FormUrlEncoded
    @POST("urinalysis/save")
    Call<ResponseBody> ncgDatas(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                @Field("BYTIME") String bytime, @Field("PROTEINFLAG") String PROTEINFLAG, @Field("BLDFLAG") String BLDFLAG,
                                @Field("GLUCOSE") String GLUCOSE, @Field("PROTEIN") String PROTEIN, @Field("BLD") String BLD, @Field("UBG") String UBG,
                                @Field("KET") String KET, @Field("PROPORTION") String PROPORTION, @Field("REDBLOODCELL") String REDBLOODCELL,
                                @Field("HEMAMEBA") String HEMAMEBA, @Field("EPITHELIALCELL") String EPITHELIALCELL, @Field("TUBE") String TUBE,
                                @Field("REDBLOODCELLHIGH") String REDBLOODCELLHIGH,
                                @Field("HEMAMEBAHIGH") String HEMAMEBAHIGH, @Field("PATHCAST") String PATHCAST,
                                @Field("SREC") String SREC,
                                @Field("language") String language
    );
    /**
     * 获取尿常规数据
     */
    @GET("urinalysis/findBytime")
    Call<ResponseBody> ncgDetail(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 提交尿蛋白参数
     */
    @FormUrlEncoded
    @POST("urineprotein/save")
    Call<ResponseBody> ndbdlDatas(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("BYTIME") String bytime, @Field("UPVALUEFLAG") String UPVALUEFLAG, @Field("UPVALUE") String UPVALUE, @Field("language") String language);

    /**
     * 获取尿蛋白参数
     */
    @GET("urineprotein/findBytime")
    Call<ResponseBody> ndbDetail(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 获取血常规参数
     */
    @GET("cbc/findBytime")
    Call<ResponseBody> xcgDetail(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 提交血常规参数
     */
    @FormUrlEncoded
    @POST("cbc/save")
    Call<ResponseBody> xcgSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                 @Field("BYTIME") String bytime, @Field("OXYPHORASEFLAG") String OXYPHORASEFLAG, @Field("WBC") String wbc,
                                 @Field("RBC") String rbc, @Field("NEUTROPHIL") String NEUTROPHIL, @Field("NEUT") String neut, @Field("OXYPHORASE") String strox,@Field("language") String language);

    /**
     * 提交免疫球蛋白参数
     */
    @FormUrlEncoded
    @POST("immune/save")
    Call<ResponseBody> myqdbSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                   @Field("BYTIME") String bytime, @Field("IGG") String igg, @Field("IGA") String iga, @Field("IGM") String igm,
                                   @Field("IGK") String igk, @Field("IGR") String igr, @Field("COQ") String coq, @Field("BTB") String btb,
                                   @Field("CH50") String ch50, @Field("BTC3") String btc3, @Field("BTC4") String btc4, @Field("CFYDB") String cfydb,@Field("language") String language);

    /**
     * 获取免疫球蛋白参数
     */
    @GET("immune/findBytime")
    Call<ResponseBody> myqdbdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                  @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);


    /**
     * 提交肾功能参数
     */
    @FormUrlEncoded
    @POST("renalf/save")
    Call<ResponseBody> sgnSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                 @Field("BYTIME") String bytime, @Field("UREANITROGENFLAG") String UREANITROGENFLAG,
                                 @Field("CREATININEFLAG") String CREATININEFLAG, @Field("UREANITROGEN") String UREANITROGEN,
                                 @Field("CREATININE") String CREATININE, @Field("UA") String UA, @Field("MICROGLOBULIN") String MICROGLOBULIN,
                                 @Field("CYSTATINC") String CYSTATINC, @Field("HCY") String HCY,@Field("language") String language);

    /**
     * 获得肾功能参数
     */
    @GET("renalf/findBytime")
    Call<ResponseBody> sgndatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 肝功能提交数据
     */
    @FormUrlEncoded
    @POST("liverf/save")
    Call<ResponseBody> ggnSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                 @Field("BYTIME") String bytime, @Field("ALTFLAG") String ALTFLAG, @Field("ALT") String ALT,
                                 @Field("PAT") String PAT, @Field("AST") String AST, @Field("TPO") String TPO, @Field("RICIM") String RICIM,
                                 @Field("GLOBULOSE") String GLOBULOSE, @Field("TBIL") String TBIL, @Field("TRIGLYCERIDE") String TRIGLYCERIDE,
                                 @Field("CHOLESTEROL") String CHOLESTEROL, @Field("HDL") String HDL,@Field("language") String language);

    /**
     * 肝功能获取数据
     */
    @GET("liverf/findBytime")
    Call<ResponseBody> ggndatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 电解质提交数据
     */
    @FormUrlEncoded
    @POST("electrolyte/save")
    Call<ResponseBody> djzSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                 @Field("byTime") String bytime, @Field("calciumFlag") String calciumFlag, @Field("potassiumFlag") String potassiumFlag,
                                 @Field("phosphorusFlag") String phosphorusFlag, @Field("potassium") String potassium, @Field("sodium") String sodium,
                                 @Field("chlorin") String chlorin, @Field("magnesium") String magnesium, @Field("calcium") String calcium,
                                 @Field("phosphorus") String phosphorus, @Field("carbonDioxide") String carbonDioxide, @Field("AG") String AG,@Field("language") String language);

    /**
     * 电解质获取数据
     */
    @GET("electrolyte/findBytime")
    Call<ResponseBody> djzdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 肾脏彩超提交数据
     */
    @FormUrlEncoded
    @POST("kidneycte/save")
    Call<ResponseBody> szccSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("BYTIME") String bytime, @Field("LEFTKIDNEYFLAG") String LEFTKIDNEYFLAG, @Field("RIGHTKIDNEYFLAG") String RIGHTKIDNEYFLAG,
                                  @Field("LEFTKIDNEY") String LEFTKIDNEY, @Field("RIGHTKIDNEY") String RIGHTKIDNEY, @Field("RCTK") String RCTK,
                                  @Field("TBAA") String TBAA, @Field("TBSES") String TBSES, @Field("TLKBF") String TLKBF, @Field("TRKBF") String TRKBF,
                                  @Field("CSYSTEM") String CSYSTEM,@Field("language") String language);

    /**
     * 肾脏彩超获取数据
     */
    @GET("kidneycte/findBytime")
    Call<ResponseBody> szccdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 乙肝五项提交数据
     */
    @FormUrlEncoded
    @POST("hbv/save")
    Call<ResponseBody> ygwxSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("byTime") String bytime, @Field("colorFlag") String colorFlag, @Field("HBSAG") String HBSAG,
                                  @Field("HBS") String HBS, @Field("HBEAG") String HBEAG, @Field("HBE") String HBE, @Field("HBC") String HBC,@Field("language") String language);

    /**
     * 乙肝五项获取数据
     */
    @GET("hbv/findBytime")
    Call<ResponseBody> ygwxdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);

    /**
     * 抗体系列获取数据
     */
    @FormUrlEncoded
    @POST("antibody/save")
    Call<ResponseBody> ktxlSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("byTime") String bytime, @Field("ANA") String ANA, @Field("ANTIPM1") String ANTIPM1, @Field("ANTISA") String ANTISA,
                                  @Field("ZXLXBBJ") String ZXLXBBJ, @Field("ANTSCL70") String ANTSCL70, @Field("ANTSSA") String ANTSSA, @Field("ANTSSB") String ANTSSB,
                                  @Field("U1RNP") String U1RNP, @Field("DNA") String DNA, @Field("ACA") String ACA,@Field("language") String language);

    /**
     * 抗体系列获取数据
     */
    @GET("antibody/findBytime")
    Call<ResponseBody> ktxldatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);


    /**
     * 病毒检测提交数据
     */
    @FormUrlEncoded
    @POST("virus/save")
    Call<ResponseBody> bdjcSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("byTime") String bytime, @Field("colorFlag") String colorFlag, @Field("EB") String EB, @Field("SH") String SH,
                                  @Field("AIDS") String AIDS, @Field("CELL") String CELL,@Field("language") String language);

    /**
     * 病毒检测获取数据
     */
    @GET("virus/findBytime")
    Call<ResponseBody> bdjcdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("byTime") String searchDate);


    /**
     * 凝血检测提交数据
     */
    @FormUrlEncoded
    @POST("cruor/save")
    Call<ResponseBody> nxjcSubmit(@Field("zxys_userName") String phone, @Field("zxys_encrypt") String secret, @Field("USER_ID") String user_id,
                                  @Field("byTime") String bytime, @Field("colorFlag") String colorFlag, @Field("TT") String TT,
                                  @Field("PT") String PT, @Field("FIB") String FIB, @Field("APTT") String APTT, @Field("DD") String DD,@Field("language") String language);


    /**
     * 凝血检测获取数据
     */
    @GET("cruor/findBytime")
    Call<ResponseBody> nxjcdatas(@Query("zxys_userName") String phone, @Query("zxys_encrypt") String secret,
                                 @Query("USER_ID") String user_id, @Query("searchDate") String searchDate);
}
