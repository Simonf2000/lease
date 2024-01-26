package com.atguigu.lease.web.admin.custom.interceptor;
import com.atguigu.lease.context.LoginUserContext;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.common.utils.ThreadLocalUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: smionf
 * @Date: 2024/01/25/11:31
 * @Description:
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("access_token");

        if (StringUtils.isBlank(token)){
            //没有牌
            throw  new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        String username = (String) JwtUtil.parseToken(token).get("username");

        ThreadLocalUtils.put(username);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContext.clear();
    }
}