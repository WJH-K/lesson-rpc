package com.lesson.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisHelper {

    private final static Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    /**
     * 生成redis的key
     * @param name
     * @param object
     * @return
     */
    public static String getRedisKey(String name, Object object) {
        String key = name+"-%s";
        try {
            if (object != null) {
                String json = JsonSerializer.toJson(object);
                //md5双重加密
                String md5 = MD5Utils.MD5(MD5Utils.MD5(json));
                //截取md5加密的key前8位，防止key过长
                key = String.format(key,md5.substring(0,8));
                logger.info("成功生成redis key={}",key);
            }
        } catch (Exception e) {
            logger.error("生成redis key异常：name={}",name,e);
            e.printStackTrace();
        }
        return key;
    }
}
