package com.zhixinyisheng.user.util.lab;

import java.util.Arrays;

/**
 * 正确的汉字
 * Created by 焕焕 on 2017/3/4.
 */
public class LabChineseUtil {
    public static final String[] arr = new String[]{"红细胞计数","白细胞计数","上皮细胞计数","管型","细菌"
            ,"红细胞(高倍视野)","包细胞(高倍视野)","上皮细胞(高倍视野)","管型(低倍视野)","细菌(高倍视野)"
            ,"病理管型","小圆上皮细胞","YLC","结晶检查","粘液丝","电导率","红细胞信息","红细胞数","红细胞变形率"
            ,"葡萄糖","尿蛋白","胆红素","尿胆原","PH","潜血","酮体","亚硝酸盐","WBC(esterase)","尿比重","浑浊度","颜色"};
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
}
