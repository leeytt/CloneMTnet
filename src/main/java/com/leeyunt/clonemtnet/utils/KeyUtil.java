package com.leeyunt.clonemtnet.utils;

import java.util.Random;

/**
 *
 * 自定义主键生成工具
 *
 * @author leeyunt
 * @since 2020/04/11
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：当前时间+随机数
     * @return String
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;//生成6位随机数
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
