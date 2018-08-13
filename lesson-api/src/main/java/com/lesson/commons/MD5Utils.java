package com.lesson.commons;


import com.lesson.modelInfo.shop.ShopReq;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ZS on 2016/4/15.
 */
public class MD5Utils {

    public static final String MD5(String s){
        char hexDigits[] = {
                '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
        };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) throws Exception{
        //System.out.println(getPoliceStatusMD5());
        System.out.println(MD5Utils.MD5("2454351sdafdas"));
        ShopReq shopReq = new ShopReq();
        //shopReq.setId(1);
        System.out.println(RedisHelper.getRedisKey("shopPage",shopReq));
    }
    public static String getPoliceStatusMD5(Map<String ,String> map,String appkey) throws NoSuchAlgorithmException {
        //拼接字符串
        StringBuffer code = new StringBuffer();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext())
        {
            String key = it.next();
            code.append(map.get(key));
        }
        code.append(appkey);
        //生成签名
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(code.toString().getBytes());
        String signature = new BigInteger(1, md5.digest()).toString(16);
        signature = addZeroForNum(signature, 32);
        return  signature;
    }

    public static String getPoliceStatusMD5()throws Exception{
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("appid", "68");
      /*  map.put("card_url","http://odp.mmarket.com/s.do?requestid=query_comment_cs&contentid=330000000803");
        map.put("userid", "15919846015");
        map.put("ip", "127.0.0.1");
        map.put("send_time", "1502695929");
        map.put("card_title", "测试数据上传接口");
        map.put("card_id", "55636302");
        map.put("card_type", "3");
        map.put("card_content", "测试数据上传接口，该请求为JAVA请求");
        map.put("citysign", "YUE0001");
        map.put("username", "15919846015");
        map.put("add_time", "1502695929");
        map.put("data_type", "2");*/
        return  getPoliceStatusMD5(map,"786d0adf08b6d134034644de52acac2c");
    }
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
           // sb.append("0").append(str);// 左补0
            sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
}
