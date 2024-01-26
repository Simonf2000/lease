package com.atguigu.lease.common.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: smionf
 * @Date: 2024/01/26/20:17
 * @Description:
 */
public class ThreadLocalUtils {
   private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get() {
        return threadLocal.get();
    }

    public static void put(String data) {
        threadLocal.set(data);
    }
}
