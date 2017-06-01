package com.zhixinyisheng.user.config;

/**
 * 网络请求的标识符
 * Created by Yuanyx on 2017/1/4.
 */

public class HttpIdentifier {
    //判断是否修改了荣誉和简介
    public static boolean sIsSaveInfo=false;
    //判断是否修改了 动态
    public static boolean IS_AMEND_INFO_DYNAMIC=false;
    public static String PAGECOUNT = "10";
    public static final int SEARCH_DOCTOR = 100;
    //科室
    public static final int SECTION = 101;
    //举报医生
    public static final int APPEAL_DOCTOR = 102;
    //医生荣誉
    public static final int HONOR_DOCTOR = 103;
    //医生简介
    public static final int INTRODUCE_DOCTOR = 104;
    //医生动态
    public static final int DYNAMIC_DOCTOR = 105;
    //医生动态详情
    public static final int DYNAMIC_DOCTOR_DETAIL = 106;
    //点赞
    public static final int CLICK_PRAISE = 107;
    //发布动态
    public static final int PUBLISH_DYNAMIC = 108;
    //医生信息
    public static final int DOCTOR_INFO = 109;
    //删除医生动态
    public static final int DELETE_DOCTOR_DYNAMIC=110;
    //我的服务列表
    public static final int DOCTOR_SERVICE=111;
    //评价医生查询接口
    public static final int EVALUATE_DOCTOR_INFO=112;
    //评价医生接口
    public static final int EVALUATE_DOCTOR=113;
    //生成订单
    public static final int CREATE_ORDER=114;
    //得到当前聊天的条数
    public static final int GET_CHAT_NUMBER=115;
    //上传聊天条数
    public static final int UPLOADING_CHAT_NUMBER=116;
    //删除我的订单
    public static final int DELETE_ORDER=117;
    //天气
    public static final int WEATHER=118;
    //
    public static final int SYNTHESIZE_INFO=119;
    //保存用药信息
    public static final int SAVE_USE_MEDICINE_INFO=120;
    //根据日期查询数据
    public static final int FIND_BY_TIMETO_DATA=121;
    //修改用药信息
    public static final int EDIT_USE_MEDICINE_INFO=122;
    //删除
    public static final int DELETE_USE_MEDICINE_INFO=123;
    //用药状态修改
    public static final int EDIT_USE_MEDICINE_STATE=124;
    //用户情况实时提醒-修改
    public static final int IS_ALL_ALERT_EDIT=125;
    //查询已输入药名
    public static final int SELECT_EDIT_MEDICINE_NAME=126;









    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //医生个性化收费
    public static final int PERSONALIZED_CHARGE_DOCTOR = 1000;
    //医生查询个性化收费
    public static final int SEARCH_PERSONALIZED_CHARGE_DOCTOR = 1001;
    //根据日期查询之前30天的数据(体温)
    public static final int TEMPERATURE_TIME_BEFORELIST = 1002;
    //根据用户ID查询有数据的日期
    public static final int TEMPERATURE_HAVE_DATA = 1003;
    //收入明细
    public static final int SEARCH_INCOME = 1004;
    //支出明细
    public static final int SEARCH_PAY = 1005;
    //根据日期查询之前30天的数据(血糖)
    public static final int BLOODSUGER_TIME_BEFORELIST = 1006;
    //根据用户ID查询有数据的日期（血糖）
    public static final int BLOODSUGER_HAVE_DATA = 1007;
    //肺活量数据添加
    public static final int PULMONARY_ADD = 1008;
    //根据用户ID查询有肺活量数据的日期
    public static final int PULMONARY_HAVA_DATA = 1009;
    //根据当天日期查询肺活量数据
    public static final int PULMONARY_CHECK_DATA = 1010;
    //肺活量折线图
    public static final int PULMONARY_CHECK_ZXT = 1011;
    //推荐医生
    public static final int RECOMMEND_DOCTOR = 1012;
    //购买服务
    public static final int PURCHASE_SERVICE = 1013;
    //我的收入
    public static final int MYINCOME = 1014;
    public static final int SEARCH_HOSPITAL = 1015;
    //消息列表里的订单消息的已读接口
    public static final int READ_ORDER_MESSAGE = 1016;
    //在我的患者列表里添加到关注列表里
    public static final int ADD_MYPATIENT_TO_CARELIST = 1017;
    //在我的医生列表里添加到关注列表里
    public static final int ADD_MYDOCTOR_TO_CARELIST = 1018;

    //查询科室列表
    public static final int SEARCH_SECTION_LIST = 1019;
    //提现
    public static final int GETMONEY = 1020;
    // 生成订单接口  （当付费为0时）
    public static final int ZERO_RECORD = 1021;
    //查看聊天信息
    public static final int CHAT_INFO = 1022;
    //消息列表里重要通知的已读接口
    public static final int READ_NOTICE_MESSAGE = 1023;
    //订单详情（支出）
    public static final int BILL_DETAIL_EXPEND = 1024;
    //化验单保存
    public static final int LABORATORY_SAVE = 1025;
    //化验单查询
    public static final int LABORATORY_LIST = 1026;
    public static final int LABORATORY_IMPORTANCE = 1027;
    //折线图
    public static final int ZHEXIANTU = 5;
    public static final int SEND_EMARL = 1028;
    public static final int EDIT_LANGUAGE = 1029;
    public static final int HEARTRATE_LIST = 1030;
    public static final int BLOOD_LIST = 1031;
    public static final int LOGIN = 1032;
    public static final int CANCEL_CARE = 1033;//取消关注
    public static final int DELETE_CONTACT = 1034;//删除好友
    public static final int ADD_MYFRIENDS_TO_CARELIST = 1035;
    public static final int STEP_SORT = 1036;
    public static final int BLOOD_PRESSURE_MESSURE = 1037;
    public static final int HEART_RATE_MESSURE = 1038;

    public static boolean IS_CHANGE_HEART_DATA=false;//判断是否修改了心率数据
    public static boolean IS_CHANGE_BLOOD_DATA=false;//判断是否修改了血压数据
    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}
