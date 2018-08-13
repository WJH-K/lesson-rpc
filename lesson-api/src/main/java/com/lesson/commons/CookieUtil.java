package com.lesson.commons;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by denghuiming on 2015/12/8.
 */
public class CookieUtil {
    /**
     * 根据cookie的名称获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())){
                //&& request.getServerName().equals(cookies[i].getDomain())) {
                return cookies[i];
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name){
        Cookie ck = getCookie(request, name);
        if(ck!=null){
            return ck.getValue();
        }else{
            return null;
        }
    }

    /**
     * 删除cookie
     * @param request
     * @param response
     * @param cookie
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 设置cookie
     * @param request
     * @param response
     * @param name
     * @param value
     * 如果不设置时间，默认永久
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value) {
        setCookie(request, response, name, value, 0x278d00);
    }


    /**
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * 设置cookie，设定时间
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, value == null ? "" : URLEncoder.encode(value.replaceAll("\r\n", ""), "utf-8"));
        } catch (UnsupportedEncodingException e) {
        }
        cookie.setMaxAge(maxAge);
        cookie.setPath(getPath(request));
        response.addCookie(cookie);
    }

    private static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }

}
