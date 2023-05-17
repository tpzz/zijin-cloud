package com.zijin.graphql.util;

/**
 * 通用方法类
 */
public class CommonUtil {

    /**
     * 判空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || "".equals(obj)) {
            return true;
        }
        return false;
    }

    /**
     * 判非空
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }




}
