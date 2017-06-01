package com.hyphenate.easeui.utils;

import android.content.Context;

import com.and.yzy.frame.util.SPUtils;

import java.util.Locale;

/**
 * Created by 焕焕 on 2017/3/8.
 */
public class LanguageUtil {


    public static String judgeLanguage(Context context) {
        SPUtils spUtils = new SPUtils("language");
        String langStr = (String) spUtils.get("language", "auto");
        if (!"auto".equals(langStr)) {
            return langStr;
        } else {
            Locale locale = context.getResources().getConfiguration().locale;
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
}
