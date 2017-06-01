package com.and.yzy.frame.util;

import java.text.DecimalFormat;

/**
 * 数字格式化工具
 */
public class NumberFormatUtil {
    /**
     * 保留小数点后面两位
     * @param number
     * @return
     */
    public static String keepPointTwo(double number) {

        DecimalFormat df = new DecimalFormat("##0.00");

        return df.format(number);
    }
    /**
     * 保留小数点后面两位
     * @param number
     * @return
     */
    public static double keepPointTwoToDouble(double number) {

        DecimalFormat df = new DecimalFormat("##0.00");

        return Double.parseDouble(df.format(number));
    }


}
