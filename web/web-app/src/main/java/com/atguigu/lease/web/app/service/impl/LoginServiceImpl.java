package com.atguigu.lease.web.app.service.impl;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.web.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Client client;

    /**
     * @Description:
     * @Param: [phone]
     * @return: void
     * @Author: simonf
     * @Date: 2024/1/27
     */
    @Override
    public void sendCode(String phone) {

        //1. 检查手机号码是否为空
        if (!StringUtils.hasText(phone)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        //2. 检查Redis中是否已经存在该手机号码的key
        //检查时间间隔
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        boolean hasKey = redisTemplate.hasKey(key);
        //若存在，则检查其存在的时间
        if (redisTemplate.hasKey(key)) {
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        //生成随机验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(9));
        }

        String code = sb.toString();

        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setPhoneNumbers(phone);
        smsRequest.setSignName("阿里云短信测试");
        smsRequest.setTemplateCode("SMS_154950909");
        smsRequest.setTemplateParam("{\"code\":\"" + code + "\"}\n");
        try {
            client.sendSms(smsRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //将验证码存到Redis
        redisTemplate.opsForValue().set(key, code, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }
}