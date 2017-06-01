package com.zhixinyisheng.user.util;

import com.zhixinyisheng.user.domain.FindByPidEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class Content {
    public static int MAX_COUNT = 200;
    public static int isFinish = 0;//判断按钮是编辑还是完成,0是编辑,1是完成
    public static int isFirstLogin = 0;//判断是不是第一次登录,0不是,1是
    public static String XVORXY;//0是心率，1是血压

    public static String DATA=new SimpleDateFormat("yyyy-MM-dd").format(new Date());//日历当前选中的日期
    public static String NowDATA=new SimpleDateFormat("yyyy-MM-dd").format(new Date());//手机当前的日期
    public static int mSelYear=-1;//日历当前选中的年
    public static int mSelMonth=-1;//日历当前选中的月
    public static int mSelDay=-1;//日历当前选中的日
//    public static int friendsHeight=-1;//关注的好友列表控件的高度

//    public static String USERID  = UserManager.getUserInfo().getUserId();
    public static int myHealthFlag = 0;

    public static boolean flagSos = false;
    public static List<FindByPidEntity.ListBean> listDate;//日历的集合
    public static int cbc = 0;
    public static int urinalysis = 0;
    public static int urineprotein= 0;
    public static int blood = 0;
    public static int bloodsugar = 0;
    public static int animalHeat = 0;
    public static int vitalCapacity = 0;
    public static int stepnumber = 0;
    public static int sleep = 0;
    public static int bmi = 0;
    public static int cruor = 0;
    public static int hbv = 0;
    public static int heartrate = 0;
    public static int immune = 0;
    public static int kidneycte = 0;
    public static int liverf = 0;
    public static int renalf = 0;
    public static int virus = 0;
    public static int antibody = 0;
    public static int electrolyte  = 0;
    public static int tongue = 0;


    public static int READ_XIEYI = 0;//知心医生用户协议读没有读（0为未读，1为已读）
    public static String RSA2_PRIVATE="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCO9+tM2AW4tDIyRQ52spctx4dBOyM9K29GJuEJ+bBWWveBuFnGKoFRgVSjWas9HJDD9m+Rqj91Mb4a//8NcU5SM+bN4Q/irlbYB7Gn0tGFD5o8kU5IzEyclcNvHTmLeDsSEPpUt9vKF9ecf5397X4wEMZmXOw2jsFE8yKV5+MIKjOJzfa/34redXOaqFrFdnCNiV6wYzFEIhcFjvKrYsTsBNxq2p1ZrNuWlOor66iZ0Z1Pl6pWbEeHmCIcNkcfi+pqb5lhHTlmUd4L+MWprjYDJBDytjd0hJFlyKOVgLCFqu2Kv4NZxOMFRtffN69HenUaOA3IkyKol6PDiDv0xCmZAgMBAAECggEAArfX82gQA8j5Q1doxPz0PtySR702GE2hGfZXRPdIUPv9xKuamVfpiYEp+MkVpl3Kqs8eo+hWuIWvlZfUY48J7CosczGaObcS05yh1gz+CseXKRuiH2Qy+K8oPg6SL4KjrN5BxCaSuS7m3ITYniFLyuImw390xjzEk+kDeVIlUNxlHm15fk4bg/32IXr1N0ocEDQd7owcT8f6o9+j1VBOHhUJ23gwVKgckG1GCEgC518qAyIi9SoXFl6EGLFSlWZhk9Ai9SJoqtgUHN7JmyeGI3nJRX9WrWxAkUJ8a24V9+C8PjXS0TIYXXuBvMK0qEVMckziF74T3pRh9cM+e9wAYQKBgQDp5FNEWqIdEyY/BlnSTF/nKYex0Wf7lvcogKoqPw6Hao9QNW3QcXJblU8KYhcUchsDox3W2tN7svsaMkxwYTgvXXRjEdqwB5KM9ut391p9A5ZnEC5uwSXoIgneSTKbgSLdCDO1Cv0DOyRr8gR6Wn5qAV2UccFgd/Hw/fCNcKHKrQKBgQCce3GsRuoMzD0ZktmwsRxsJr5B5YwWDufds64xArILtJYFvDIUpL9iuCnWbcbGadtMXFEgd67vEydp38KKKjMRTY3AHatcUq1zZ3X9K1CUoeFWNJaKfv4ZIj6hkNLuZi9IMznSnk+Q+w9E1a/VZJHhRCMgCsPd/0khsGNx4ouEHQKBgBvIlJ+VCJbdlHh+H6NckTPbehQEZQ680yVLi2szTF2HqfiEMERHhacdaGM6XbV/DHh1IvFH00FpdGybNvtOgScXOeEe2NR6Qc1e83+vsV8SUwZxJKLhbMhTasqt9UYFpAePltzVrlJ47w2nlSKXp1aeTOh6TtdPyDyxXabfj0iNAoGAazIJUeoWZYP4Cy/VHryFavUzenLkfyvg3Lg9COf+zhP8fZtJd2jQjrD4QCNdewiDKRFIpgQ2+yHU6ytU2k9EQoMtKsFoZUth9N+YLUFh5x/p/KLzyZZrPQ5/dzB9vsCTfVNSNh0rBndmI1J7LKTDZlzF7ypQuSArmf96cEawKmUCgYBqX6w/VKKmrWWEgZxHg1hKM/zYndunahNHmGnPJcv6mMtp4v2p52gHRYdFSk6+NRc3ib+ll7qarPLMBBqT1oh3Yeluu/iiqNVqnWL53KWsazKtkwxEyU6FepG/L30NMdoyGUkT/65VN1Ihwb501j5M8cNHVrqv1dF2TtNTj2zJaA==";



    public static final String APP_ID = "wx54a16495034d3dd2";//微信分享所用
    public static final String QQ_ID = "1105772631";//QQ分享所用

    public static int TONG_XUN_LU=0;//判断通讯录中加没加好友 0为没加

    //以下为化验单选项
    public static String chineseName = "";//中文名称
    public static String resultData = "";//结果数值
    public static String unit = "";//单位
    public static String time_lab = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//手写化验单的时间

    public static int isLabNull = -1;//化验单数据是否为空
}
