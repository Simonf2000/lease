package com.atguigu.lease.context;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: smionf
 * @Date: 2024/01/25/13:57
 * @Description:
 */
public class LoginUserContext {

    private static final ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();

    public static void setLoginUser(LoginUser loginUser) {
        userThreadLocal.set(loginUser);
    }

    public static LoginUser getLoginUser() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}