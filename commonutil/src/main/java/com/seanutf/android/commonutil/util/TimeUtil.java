package com.seanutf.android.commonutil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


    private final static long SECOND = 1;
    private final static long MINITE = 60 * SECOND;
    private final static long HOUR = 60 * MINITE;
    private final static long DAY = 24 * HOUR;
    private final static long MONTH = 30 * DAY;
    private final static long YEAR = 12 * MONTH;

    private static final String yearMonthDateHourMinuteSecond1 = "yyyy-MM-dd HH:mm:ss";
    private static final String yearMonthDateHourMinuteSecond2 = "yyyy.MM.dd HH:mm:ss";
    private static final String yearMonthDateHourMinuteSecond3 = "yyyy.MM.dd HH:mm";
    private static final String yearMonthDateHourMinuteSecond4 = "yyyy MM dd HH mm";

    private static final String yearMonthDateHourMinute1 = "yyyy年MM月dd日 HH:mm";
    private static final String yearMonthDateHourMinute2 = "yyyy-MM-dd HH:mm";

    private static final String monthDateHourMinute1 = "MM月dd日 HH:mm";

    private static final String monthDate1 = "MM-dd";

    /**
     * 获取当前的时间戳
     *
     * @return long型时间戳
     */
    public static long getCurrentTime() {
        long millis = System.currentTimeMillis();
        return millis;
    }

    /**
     * 秒值转化为月日格式
     *
     * @param seconds
     */
    public static String parseSecondToString(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(monthDate1);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }


    /**
     * 秒值转化为月日格式
     *
     * @param seconds
     */
    public static String parseSecond2ToString(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinuteSecond2);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 秒值转化为月日格式
     *
     * @param seconds
     */
    public static String parseSecond3ToString(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinuteSecond3);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 秒值转化为月日格式
     *
     * @param seconds
     */
    public static String parseSecond4ToString(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinuteSecond4);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 秒值转化为年月日时分格式
     *
     * @param seconds
     */
    public static String parseTimeToString1(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinute1);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 秒值转化为年月日时分秒格式
     *
     * @param seconds
     */
    public static String parseTimeToString2(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinuteSecond1);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 秒值转化为年月日时分秒格式
     *
     * @param seconds
     */
    public static String parseTimeToString3(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinute2);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 添加商品的入口的时间转化的
     *
     * @param time
     * @return
     */
    public static int parseString3ToIntTime(String time) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(yearMonthDateHourMinute2);
            calendar.setTime(dateFormat.parse(time));
            return (int) (calendar.getTimeInMillis() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 秒值转化为月日时分格式
     *
     * @param seconds
     */
    public static String parseTimeToString4(long seconds) {
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(monthDateHourMinute1);
        Date date = new Date(seconds * 1000);
        //指定格式的时间串
        return dateFormat.format(date);
    }

    /**
     * 通过时间戳获得日期，需设置返回的格式
     *
     * @param time      秒
     * @param dateStyle 所需格式化
     * @return
     */
    public static String getDateForTime(long time, String dateStyle) {
        SimpleDateFormat sdr = new SimpleDateFormat(dateStyle);
        @SuppressWarnings("unused")
        String times = sdr.format(new Date(time * 1000L));
        return times;
    }

    public static String firendTimeAgo(long timestamp) {

        long curStamp = System.currentTimeMillis() / 1000;
        long delta = curStamp - timestamp;
        if (delta <= 0) {
            return "刚刚";
        } else {
            if (delta < 1 * MINITE) {
                return "刚刚";
            } else if (delta < 2 * MINITE) {
                return "1分钟前";
            } else if (delta < 60 * MINITE) {
                long minites = delta / MINITE;
                return String.format("%d分钟前", minites);
            } else if (delta < 90 * MINITE) {
                return "1小时前";
            } else if (delta < 24 * HOUR) {
                long hours = delta / HOUR;
                return String.format("%d小时前", hours);
            } else if (delta < 48 * HOUR) {
                return "昨天";
            } else if (delta < 30 * DAY) {
                long days = delta / DAY;
                return String.format("%d天前", days);
            } else if (delta < 12 * MONTH) {
                long months = delta / MONTH;
                return months <= 1 ? "1个月前" : String.format("%d个月前", months);
            } else {
                long years = delta / YEAR;
                return years <= 1 ? "1年前" : String.format("%d年前", years);
            }
        }

    }

    /**
     * 将 以秒为单位的间隔时间 格式化为字符串显示,从天开始算
     *
     * @param gapTime  间隔时间，单位秒
     * @param showDays 是否展示天
     * @param show0Day 是否展示0天
     */
    public static String parseGapTimeForDays1(long gapTime, boolean showDays, boolean show0Day) {
        long day = gapTime / DAY;
        long hour = gapTime % DAY / HOUR;
        long minute = gapTime % DAY % HOUR / MINITE;
        long second = gapTime % DAY % HOUR % MINITE / SECOND;

        StringBuilder timeStr = new StringBuilder();
        //        //String timeStr = "";
        //        String hourStr = "";
        //        String minuteStr = "";
        //        String secondStr = "";
        if (showDays) {
            if (show0Day || day != 0) {
                timeStr.append(day + "天");
            }

            if (hour < 10) {
                if ((timeStr.toString().contains("天") && hour == 0) || hour > 0) {
                    timeStr.append("0" + hour + "时");
                }
            } else {
                timeStr.append(hour + "时");
            }
        } else {

            if (hour > 0) {
                timeStr.append(hour + "时");
            }
        }


        if (minute < 10) {
            timeStr.append("0" + minute);
        } else {
            timeStr.append(minute);
        }

        if (second < 10) {
            timeStr.append(":0" + second);
        } else {
            timeStr.append(":" + second);
        }

        return timeStr.toString();
    }

    /**
     * 将 以秒为单位的间隔时间 格式化为字符串显示,从小时开始算
     *
     * @param gapTime 间隔时间，单位秒
     */
    public static String parseGapTimeForMinutes1(long gapTime) {

        if (gapTime <= 0) {
            return "00:00";
        }

        long minute = gapTime % DAY % HOUR / MINITE;
        long second = gapTime % DAY % HOUR % MINITE / SECOND;

        StringBuilder timeStr = new StringBuilder();

        if (minute < 10) {
            timeStr.append("0" + minute);
        } else {
            timeStr.append(minute);
        }

        timeStr.append(":");

        if (second < 10) {
            timeStr.append("0" + second);
        } else {
            timeStr.append("" + second);
        }

        return timeStr.toString();
    }

    /**
     * 将 以秒为单位的间隔时间 格式化为字符串显示,从小时开始算
     *
     * @param gapTime 间隔时间，单位毫秒
     */
    public static String parseGapTimeForHours(long gapTime) {
        gapTime = gapTime / 1000;

        long hour = gapTime % DAY / HOUR;
        long minute = gapTime % DAY % HOUR / MINITE;
        long second = gapTime % DAY % HOUR % MINITE / SECOND;

        StringBuilder timeStr = new StringBuilder();

        if (hour < 10) {
            timeStr.append("0" + hour);
        } else {
            timeStr.append("" + hour);
        }
        timeStr.append(":");

        if (minute < 10) {
            timeStr.append("0" + minute);
        } else {
            timeStr.append(minute);
        }

        timeStr.append(":");

        if (second < 10) {
            timeStr.append("0" + second);
        } else {
            timeStr.append("" + second);
        }

        return timeStr.toString();
    }

    public static int[] getCurrentTimeArray() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int day = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        int[] timeArray = new int[5];

        timeArray[0] = year;
        timeArray[1] = month + 1;
        timeArray[2] = date;
        timeArray[3] = day;
        timeArray[4] = minute;

        return timeArray;
    }
}
