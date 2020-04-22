package com.leeyunt.clonemtnet.utils;

/**
 * 计算工具类
 */
public class MathUtil {
    private static final Double MODEY_RANGE = 0.01;

    /**
     * 比较两个金额是否相等
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if (result < MODEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }
}
