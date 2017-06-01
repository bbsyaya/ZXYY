package com.zhixinyisheng.user.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证
 */
public class MatchStingUtil {

    /**
     * 判断手机号码
     */
    public static boolean isMobile(String mobiles) {
        if (mobiles.length()<11)
            return false;
        Pattern pattern = Pattern.compile("^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0-9])[0-9]{8}$");
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }
    /**
     * 判断邮箱
     * */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);


        return m.matches();
    }

}
