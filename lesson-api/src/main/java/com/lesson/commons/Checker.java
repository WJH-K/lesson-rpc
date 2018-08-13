package com.lesson.commons;


import com.lesson.commons.exception.NullOrEmptyException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangguoping on 14/12/10.
 */
public class Checker {
    public static boolean isNone(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

    public static void checkNone(Map map, String paramName) throws NullOrEmptyException {
        if (map == null || map.size() == 0) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static boolean isNone(List lst) {
        if (lst == null || lst.size() == 0)
            return true;
        return false;
    }

    public static void checkNone(List lst, String paramName) throws NullOrEmptyException {
        if (lst == null || lst.size() == 0) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static boolean isNone(String str) {
        if (str == null || str.equals(""))
            return true;
        return false;
    }

    public static void checkNone(String str, String paramName) throws NullOrEmptyException {
        if (str == null || str.equals("")) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static boolean isNone(Object object) {
        if (object == null) return true;
        return false;
    }

    public static void checkNone(Object object, String paramName) throws NullOrEmptyException {
        if (object == null) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static boolean isNone(Object[] objects) {
        if (objects == null || objects.length == 0) return true;
        return false;
    }

    public static void checkNone(Object[] objects, String paramName) throws NullOrEmptyException {
        if (objects == null || objects.length == 0) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static boolean isNone(byte[] bytes) {
        if (bytes == null || bytes.length == 0){
            return true;
        } else {
            return false;
        }
    }

    public static void checkNone(byte[] bytes, String paramName) throws NullOrEmptyException {
        if (bytes == null || bytes.length == 0) {
            throw new NullOrEmptyException(paramName + " is null or empty");
        }
    }

    public static int getIntegerValue(Integer value, int defaultValue) {
        return value != null ? value.intValue() : defaultValue;
    }

    public static byte getByteValue(Byte value, byte defaultValue) {
        return value != null ? value.byteValue() : defaultValue;
    }

    public static short getShortValue(Short value, short defaultValue) {
        return value != null ? value.shortValue() : defaultValue;
    }

    public static double getDoubleValue(Double value, double defaultValue) {
        return value != null ? value.doubleValue() : defaultValue;
    }

    public static boolean getBooleanValue(Boolean value, boolean defaultValue) {
        return value != null ? value.booleanValue() : defaultValue;
    }

    public static String getStringValue(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static long getLongValue(Long value, long defaultValue) {
        return value != null ? value.longValue() : defaultValue;
    }

    public static Date getDateValue(Date date, Date defaultDate) {
        return date != null ? date : defaultDate;
    }

    public static int getIntegerValue(Integer value) {
        return value != null ? value.intValue() : 0;
    }

    public static byte getByteValue(Byte value) {
        return value != null ? value.byteValue() : (byte)0;
    }

    public static short getShortValue(Short value) {
        return value != null ? value.shortValue() : (short)0;
    }

    public static double getDoubleValue(Double value) {
        return value != null ? value.doubleValue() : (double)0;
    }

    public static boolean getBooleanValue(Boolean value) {
        return value != null ? value.booleanValue() : false;
    }

    public static long getLongValue(Long value) {
        return value != null ? value.longValue() : 0L;
    }

    public static String NullStringToEmpty(String value) {
        return value != null ? value : "";
    }

    public static Date getDateValue(Date date) {
        return date != null ? date : new Date(System.currentTimeMillis());
    }

}
