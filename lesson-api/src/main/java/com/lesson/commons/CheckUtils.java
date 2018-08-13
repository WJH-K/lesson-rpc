package com.lesson.commons;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wwg on 2015/12/29.
 */
public class CheckUtils {
    /**
     * 延用原来校验电话号码的方法
     */
    public static  final String MOBILEPHONE_REGEX="^1\\d{10}";

    public static final String IS_EMAIL="\\w+@(\\w+.)+[a-z]{2,3}";
    public static boolean isMobilePhone(String number){
        if(StringUtils.isBlank(number)){
            return  false;
        }
        Pattern pattern=Pattern.compile(MOBILEPHONE_REGEX);
        Matcher m = pattern.matcher(number);
        return m.matches();
    }
    public static boolean isEmail(String email){
        if(StringUtils.isBlank(email)){
            return  false;
        }
        Pattern pattern=Pattern.compile(IS_EMAIL);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
    public static  void  main(String args[]){
        String key="AdContent_215_adxx";
        String [] ids=key.split("_");
        System.out.println(ids[1]+"**"+ids[2]);
    }
}
