package com.zhixinyisheng.user.util;

import com.and.yzy.frame.util.SPUtils;
import com.zhixinyisheng.user.ui.MyApplication;

import java.util.Locale;

/**
 * Created by 焕焕 on 2017/3/8.
 */
public class LanguageUtil {
    public static String judgeLanguage() {
        SPUtils spUtils = new SPUtils("language");
        String langStr = (String) spUtils.get("language", "auto");
        if (!"auto".equals(langStr)) {
            return langStr;
        } else {
            Locale locale = MyApplication.getApp().getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (language.contains("zh")) {
                return "zh";
            } else if (language.contains("en")) {
                return "en";
            } else {
                return "en";
            }
        }
    }

    public static String currentLanguage(){
        Locale locale = MyApplication.getApp().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }
}
