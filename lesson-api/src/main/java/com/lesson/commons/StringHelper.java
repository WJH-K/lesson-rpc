package com.lesson.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiangxiong Lin on 2015/4/14.
 */
public class StringHelper {
    public static final String scriptReg = "<script[\\s\\S]+?</script>";
    public static final String iframeReg = "<iframe[\\s\\S]+?</iframe>";

    public static String clearHtmlTag(String content) {
        if (!isNullOrEmpty(content)) {
            content = clearSpecialContent(content, scriptReg);
            content = clearSpecialContent(content, iframeReg);
            content = clearSpecialContent(content, "(<[^>\\s]*\\b(\\w)+\\b[^>]*>)|([\\s]+)|(<>)|(&nbsp;)");
            content = content.replace("\"", "").replace("<", "").replace(">", "");
        }
        return content;
    }

    /***
     * 替换标签
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        if (isNullOrEmpty(htmlStr)) {
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /***
     * 替换分号
     *
     * @param htmlStr
     * @return
     */
    public static String delSymbol(String htmlStr) {
        if (isNullOrEmpty(htmlStr)) {
            return "";
        }
        return htmlStr.replace("\"", "”");
    }

    public static String clearImageAndScript(String content) {
        String regexExpression = "<script[\\s\\S]+</script *>|<s*img[^>]*>";
        return clearSpecialContent(content, regexExpression);
    }

    public static String clearSpecialContent(String content, String regexExpression) {
        if (isNullOrEmpty(content)) {
            return "";
        }
        Pattern pattern = Pattern.compile(regexExpression, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        content = matcher.replaceAll("");
        return content;
    }

    public static String replaceSpecialContent(String sourceStr, String targetStr, String regexExp, int flags) {
        Pattern pattern = Pattern.compile(regexExp, flags);
        Matcher matcher = pattern.matcher(sourceStr);
        sourceStr = matcher.replaceAll(targetStr);
        return sourceStr;
    }

    public static String substringContent(String sourceStr, String regexExp, int flags) {
        Pattern pattern = Pattern.compile(regexExp, flags);
        Matcher matcher = pattern.matcher(sourceStr);
        return matcher.find() ? matcher.group() : "";
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }

    /**
     * 转半角的函数
     *
     * @param value 任意字符串
     * @return 半角字符串
     */
    public static String toDBC(String value) {
        char[] c = value.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }
    public static void main(String args[]){
        String str2="heloo   word  哈哈哈哈";
         String str="　　不合宜人群：没有查前禁忌：注意正常的生活饮食习惯，注意个人卫生。　　检查时要求：积极配合医生";
         System.out.println(str.replaceAll("　", ""));
         System.out.println((int)" ".charAt(0));
        System.out.println((int)" ".charAt(0));
        System.out.println(str2.replaceAll("\\s*", "").replaceAll("\\[",""));
    }

    /**
     * 去除空格
     * @param str
     * @param index
     * @return
     */
    public static String clearSpaceOfString(String str,int index) {
        if (Checker.isNone(str)){
            return str;
        }
        String result = str;
        if (str.length() > index) {
            String s = str.substring(0,index).trim();
            String t = s.replaceAll("\\s*","").replaceAll("　","");
            String v = t.replaceAll("&nbsp;","");
            result = v + str.substring(index);
        } else {
            result = str.replaceAll("\\s*","").replaceAll("　","").replaceAll("&nbsp;","");
        }

        return result;
    }

    /**
     * 获取某个子字符串在整个字符串中第N次出现时的下标索引
     * @param str
     * @param modelStr
     * @param count
     * @return
     */
    public static int getFromIndex(String str, String modelStr, Integer count) {
        String newString = str.replaceAll(" ","");
        String[] strings = newString.split(modelStr);
        if (strings == null || strings.length < count) {
            return -2;
        }
        int index = 0;
        for (int i = 0; i < count; i++) {
            index = index + strings[i].length();
        }
        index = index + modelStr.length()*2 - 1;
        return index;
    }
}
