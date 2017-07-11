package com.yc.parkcharge2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by a on 2017/3/29.
 */

public class DateTimeUtil {

    private static DateFormat dfDateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dfDate = new java.text.SimpleDateFormat("yyyy-MM-dd");

    public static String formatDateTime(Date date){
        return dfDateTime.format(date);
    }

    public static Date parseDateTime(String dateTime){
        try {
            return dfDateTime.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatDate(Date date){
        return dfDate.format(date);
    }

    public static Date parseDate(String dateTime){
        try {
            return dfDate.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
