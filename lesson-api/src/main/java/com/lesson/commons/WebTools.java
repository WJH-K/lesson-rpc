package com.lesson.commons;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by ZS on 2016/3/28.
 */
public class WebTools {
    public static String getOneSetTimeStr(int i) {
        String str = "";
        switch (i) {
            case 2:
                str = "最近24小时";
                break;
            case 3:
                str = "最近一个星期";
                break;
            case 4:
                str = "最近一个月";
                break;
            case 5:
                str = "最近一年";
                break;
            case 6:
                str = "一年以上";
                break;
            case 7:
                str = "不清楚";
                break;
            case 41:
                str = "最近3个月";
                break;
        }
        return str;
    }

    public static String getUrl(String userName) {
        return String.format("http://my.39.net/%s", userName);
    }

    public static String getTopicUrl(int tid) {
        return String.format("/question/%s.html", tid);
    }

    public static String getUrlByNum(int fum, int stateId, int page, boolean isRewrite) {
        if (isRewrite) {
            return String.format("/browse/%s-%s-%s.html", fum, stateId, "%s");
        } else {
            return String.format("/browse/%s-%s-%s.html", fum, stateId, page);
        }
    }

    public static String getUrl(int fum, int stateId, int sex, int age, boolean isRewrite) {
        if (isRewrite) {
            return String.format("/browse/%s-%s-%s.html", fum, stateId, "%s");
        } else {
            return String.format("/browse/%s-%s-%s.html", fum, stateId, "%s");
        }
    }

    public static String getUrlByTagId(int tagId) {
        return String.format("/browse/%s.html", tagId);
    }


    public static String getFirstString(String stringToSub, int length, String formatStr) {
        if (StringUtils.isEmpty(formatStr)) {
            formatStr = "...";
        }
        if (StringUtils.isEmpty(stringToSub)){
            return stringToSub;
        }
        char[] stringChar = stringToSub.toCharArray();
        StringBuilder sb = new StringBuilder();
        int nLength = 0;
        boolean isCut = false;
        for (int i = 0; i < stringChar.length; i++) {
            if (isChinese(stringChar[i])) {
                sb.append(stringChar[i]);
                nLength += 2;
            } else {
                sb.append(stringChar[i]);
                nLength = nLength + 1;
            }
            if (nLength >= length) {
                isCut = true;
                break;
            }
        }

        if (isCut) {
            if (formatStr != null) {
                if (isChinese(sb.charAt(sb.length() - 1))) {
                    sb.delete(sb.length() - 2, sb.length());
                } else {
                    sb.delete(sb.length() - 1, sb.length());
                }
            }
            return String.format("%s%s", sb.toString(), formatStr);
        } else {
            return sb.toString();
        }
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static String doctorFilterHtml(String html) {
        html = StringHelper.replaceSpecialContent(html, "", "<script[\\s\\S]+</script *>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", " href *=*[\\s\\S]*script *:", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, " _disibledevent=", " no[\\s\\S]*=", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "<iframe[\\s\\S]+</iframe *>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "<frameset[\\s\\S]+</frameset *>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "\\<img[^\\>]+\\>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "<Iframe[\\s\\S]+Iframe>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "<ObJect[\\s\\S]+</script *>", Pattern.CASE_INSENSITIVE);
        html = StringHelper.replaceSpecialContent(html, "", "<Param[\\s\\S]+</script *>", Pattern.CASE_INSENSITIVE);
        html = html.replace("</strong>", "");
        html = html.replace("<strong>", "");
        html = html.replace("</IFRAME>", "");
        html = StringHelper.replaceSpecialContent(html, "", "<a (.*?)>", Pattern.COMMENTS);
        html = StringHelper.replaceSpecialContent(html, "", "</a>", Pattern.CASE_INSENSITIVE);
        return html;
    }

    public static String clearTag(String html) {
        return StringHelper.clearHtmlTag(html);
    }

    /**
     * 过滤xss攻击脚本
     *
     * @param html
     * @return
     */
    public static String fileterXSS(String html) {
        if (StringUtils.isEmpty(html)) {
            return "";
        }
        String value = html;
        // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
        // avoid encoded attacks.
        // value = ESAPI.encoder().canonicalize(value);
        // Avoid null characters
        value = value.replaceAll("", "");
        // Avoid anything between script tags
        Pattern scriptPattern = Pattern.compile("(.*?)", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome  tag
        scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome  tag
        scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid eval(...) e­xpressions
        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid e­xpression(...) e­xpressions
        scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid javascript:... e­xpressions
        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid vbscript:... e­xpressions
        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid onload= e­xpressions
        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        return value;
    }

    /**
     * 获取远程访问IP
     *
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIp = null;
        if (remoteIp == null || remoteIp.length() == 0) {
            remoteIp = request.getHeader("x-forwarded-for");
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("X-Real-IP");
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("Proxy-Client-IP");
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getRemoteAddr();
            }
            if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getRemoteHost();
            }
        }
        return remoteIp;
    }

    public static String getTagType(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "疾病";
                break;
            case 2:
                result = "症状";
                break;
            case 3:
                result = "药品";
                break;
            case 4:
                result = "检查";
                break;
            case 5:
                result = "手术";
                break;
            case 6:
                result = "器官部位";
                break;
            case 7:
                result = "食品";
                break;
        }
        return result;
    }

    public static String CheckTime(Date time) {
        DateTime now = new DateTime(new Date());
        DateTime before = new DateTime(time);
        int months = Months.monthsBetween(before, now).getMonths();
        int days = Days.daysBetween(before, now).getDays();
        int hours = Hours.hoursBetween(before, now).getHours();
        int minutes = Minutes.minutesBetween(before, now).getMinutes();
        int seconds = Seconds.secondsBetween(before, now).getSeconds();
        if (minutes < 1) {
            if (seconds < 1) {
                return "1秒前";
            } else {
                return minutes + "秒前";
            }
        } else if (minutes < 60) {
            return String.format("%s分前", minutes);
        } else if (hours < 24) {
            return String.format("%s小时前", hours);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }

    public static String getDoctorLibUrl(int fid, int type, int pageIndex) {
        return String.format("/doctors/%s-%s-%s.html", fid, type, pageIndex);
    }

    public static String getAskAQuestionToUserUrl(int pid) {
        return String.format("/addTopic?tomid=%s", pid);
    }

    public static void printOutJs(HttpServletResponse response, String str) {
        response.setContentType("application/x-javascript");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        //数据组装
        try {
            out = response.getWriter();
            //  out.write(tonglanHtml(tid, fid, hasKadAD));
            out.write(str);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static String getPageNumbers(int curPage, int recordCount, int pageSize, String url, int extendPage, boolean
            isRewrite) {
        int num = 1;
        if (pageSize < 1) {
            pageSize = 1;
        }
        int num2 = recordCount / pageSize + 1;
        if (recordCount % pageSize == 0) {
            num2--;
        }
        if (num2 < 1) {
            num2 = 1;
        }
        if (curPage < 1) {
            curPage = 1;
        }
        if (curPage > num2) {
            curPage = num2;
        }
        String text = "<a href=\"%s\" class=\"first\">首页</a>&nbsp;<a href=\"%s\" class=\"prev\">上一页</a>&nbsp;";
        String text2 = "<a href=\"%s\" class=\"next\">下一页</a>&nbsp;<a href=\"%s\" class=\"last\">尾页</a>";
        if (isRewrite) {
            text = String.format(text, String.format(url, 1), String.format(url, curPage - 1));
            text2 = String.format(text2, String.format(url, curPage + 1), String.format(url, num2));
            if (curPage == 1) {
                text = "";
            }
            if (curPage == num2) {
                text2 = "";
            }
        } else {
            if (url.indexOf("?") > 0) {
                url += "&";
            } else {
                url += "?";
            }
            text = String.format(text, url + "pn=1", url + String.format("pn=%s", curPage - 1));
            text2 = String.format(text2, url + String.format("pn=%s", curPage + 1),
                    url + String.format("pn=%s", num2));
            if (curPage == 1) {
                text = "";
            }
            if (curPage == num2) {
                text2 = "";
            }
        }
        if (num2 < 1) {
            num2 = 1;
        }
        if (extendPage < 3) {
            extendPage = 2;
        }
        int num3 = 0;
        if (num2 > extendPage) {
            if (curPage - extendPage / 2 > 0) {
                if (curPage + extendPage / 2 < num2) {
                    num = curPage - extendPage / 2;
                    num3 = num + extendPage - 1;
                } else {
                    num3 = num2;
                    num = num3 - extendPage + 1;
                }
            } else {
                num3 = extendPage;
            }
        } else {
            num = 1;
            num3 = num2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        for (int i = num; i <= num3; i++) {
            if (i == curPage) {
                sb.append("<strong style=\"color:Red\">");
                sb.append(i);
                sb.append("</strong>&nbsp;");
            } else {
                sb.append("<a href=\"");
                if (isRewrite) {
                    sb.append(String.format(url, i));
                } else {
                    sb.append(url);
                    sb.append("pn=");
                    sb.append(i);
                }
                sb.append("\">");
                sb.append(i);
                sb.append("</a>");
                sb.append("&nbsp;");
            }
        }
        sb.append(text2);
        return sb.toString();
    }

    public static String getADJs(String adId, String positionId) {
        String adScript = getADJs(adId);
        if (!org.apache.commons.lang.StringUtils.isBlank(positionId)) {
            adScript = String.format("$('#" + positionId + "').writeCapture().html('%s');", adScript);
        }
        return adScript;
    }

    public static String getADJs(String adId) {
        if (StringUtils.isEmpty(adId)) {
            return "";
        }
        String adScript = "";
        if (adId.startsWith("u")) {
            adScript = GetBaiduADJs(adId);
        } else {
            adScript = GetAskADJs(adId);
        }
        return adScript;
    }

    /**
     * 百度广告
     *
     * @param adId
     * @return
     */
    public static String GetBaiduADJs(String adId) {
        if (StringUtils.isEmpty(adId)) {
            return "";
        }
        String adScript = "";
        adScript = String.format("<script type=\"text/javascript\">var cpro_id = \"%s\";</script><script src=\"http://image.39.net/client/39/c.js\" type=\"text/javascript\"></script>", adId);
        return adScript;
    }


    /// <summary>
    /// 根据广告id获取ask站内js广告
    /// </summary>
    /// <param name="adId">广告id</param>
    /// <returns></returns>
    public static String GetAskADJs(String adId) {
        String adScript = "";
        adScript = String.format("<script type=\"text/javascript\">ac_as_id = %s;ac_format = 0;ac_mode = 1;" +
                "ac_group_id = 1;ac_server_base_url = \"d-test.39.net/\";</script><script type=\"text/javascript\" " +
                "src=\"http://image.39.net/creative/k.js\"></script>", adId);
        return adScript;
    }

    /**
     * 获取热搜词url
     *
     * @param kId
     * @return
     */
    public static String getKeywordsUrl(int kId) {
        return String.format("/keywords/%s.html", kId);
    }

    /**
     * 获取当前请求完整url
     * @param request
     * @return
     */
    public static String getCurrentUrl(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getScheme())//当前链接使用的协议
                .append("://").append(request.getServerName())//服务器地址
                .append((request.getServerPort() == 80 ? "" : ":" + request.getServerPort()))//端口号
                .append(request.getContextPath())//应用名称，如果应用名称为
                .append(request.getServletPath());//请求的相对url

        if (!Checker.isNone(request.getQueryString()))//请求参数
            sb.append("?").append(request.getQueryString());

        return sb.toString();
    }
}
