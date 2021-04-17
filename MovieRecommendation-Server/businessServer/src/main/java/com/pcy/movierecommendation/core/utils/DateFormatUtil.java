package com.pcy.movierecommendation.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式转换格式类
 *
 * @author PengChenyu
 * @since 2020-12-19 16:21:42
 */
public class DateFormatUtil {


    /**
     * 获取当前时间
     *
     * @return 当前时间，格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 转化成日期 + 时间的格式： yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime 源日期
     * @return yyyy-MM-dd HH:mm:ss 类型的日期字符串
     */
    public static String toDateTime(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date formatDateTime = null;
        try {
            formatDateTime = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(formatDateTime);
    }

    /**
     * 转化成日期的格式： yyyy-MM-dd
     *
     * @param date 源日期
     * @return yyyy-MM-dd格式的日期字符串
     */
    public static String toDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate = null;
        try {
            formatDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(formatDate);
    }


    /**
     * 根据生日获取年龄
     *
     * @param birth 生日
     * @return 年龄
     * @throws ParseException
     */
    public static int getAgeByBirth(String birth) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = simpleDateFormat.parse(birth);
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthDate);
        Calendar curr = Calendar.getInstance();
        int age = curr.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if ((cal.get(Calendar.MONTH) > curr.get(Calendar.MONTH)) ||
                (cal.get(Calendar.MONTH) == curr.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) > curr.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }
}
