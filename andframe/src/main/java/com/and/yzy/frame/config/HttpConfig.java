package com.and.yzy.frame.config;

/**
 * Created by 焕焕 on 2016/4/11.
 */
public class HttpConfig {

    //    public static final String BASE_URL = "http://222.222.12.186:8091/zhixinyisheng/api/";// 旧版接口
//        public static final String BASE_URL = "http://192.168.42.216:8066/zxys_user/api/";//李聪本地
//    public static final String BASE_URL = "http://192.168.42.165:8080/zxys_user/api/";//雪蕊本地
//    public static final String BASE_URL = "http://222.223.218.50:8866/zxys_user/api/";//化验单识别测试机服务器
//    public static final String BASE_URL = "http://222.222.24.133:8088/zxys_user/api/";//测试机服务器
    public static final String BASE_URL = "http://zxys.zhixinyisheng.com:8166/zxys_user/api/";//正式线上服务器

    public static final String UPDATE_URL = BASE_URL + "version/update";

    public static final String SHARE_URL = BASE_URL + "apk/download.html";//分享的地址
    public static final String IMAGE_URL = BASE_URL + "upload/user";//图片上传的地址
    public static final String tessDataUrl = "http://www.shenbs.com/tessdata.zip";//中文识别库的下载地址


}
