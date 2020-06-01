package com.seanutf.android.commonutil.util;

/**
 * 功能描述: String字符串转数字的工具类
 * 作 者:  sean
 * 时 间： 2016/6/27 10:45
 */
public class StrToNumberUtil {
    /**
     * 字符串转int 如果字符串为空或装换异常 返回0
     *
     * @param src
     * @return
     */
    public static int strToint(String src) {
        if (null == src || src.equals("")) {
            return 0;
        }
        int number = 0;
        try {
            number = Integer.parseInt(src);
        } catch (Exception e) {
            return 0;
        }
        return number;
    }

    /**
     * 字符串转long 如果字符串为空或装换异常 返回0
     *
     * @param src
     * @return
     */
    public static long strTolong(String src) {
        if (null == src || src.equals("")) {
            return 0;
        }
        long number = 0;
        try {
            number = Long.parseLong(src);
        } catch (Exception e) {
            return 0;
        }
        return number;
    }

    /**
     * 字符串转double 如果字符串为空或装换异常 返回0
     *
     * @param src
     * @return
     */
    public static double strTodouble(String src) {
        if (null == src || src.equals("")) {
            return 0;
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(src);
        } catch (Exception e) {
            return 0;
        }
        return number;
    }

    /**
     * 字符串转float 如果字符串为空或装换异常 返回0
     *
     * @param src
     * @return
     */
    public static float strTofloat(String src) {
        if (null == src || src.equals("")) {
            return 0;
        }
        float number = 0;
        try {
            number = Float.parseFloat(src);
        } catch (Exception e) {
            return 0;
        }
        return number;
    }
}
