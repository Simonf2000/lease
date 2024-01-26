package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.common.utils.ThreadLocalUtils;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemUserService systemUserService;

    /**
     * @Description:
     * 公共方法，用于获取验证码
     * @Param: []
     * @return: com.atguigu.lease.web.admin.vo.login.CaptchaVo
     * @Author: simonf
     * @Date: 2024/1/25
     */
    @Override
    public CaptchaVo getCaptcha() {
        //用于生成验证码，specCaptcha是一个特定类型的验证码，接受三个参数：1.宽度，2.高度，3.字符数量
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String code = specCaptcha.text().toLowerCase();
        //生成一个唯一的key，用于在Redis中存储验证码。
        //admin:longin + UUID
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
       //将验证码转换成为Base64编码的字符串，用于在前端显示
        String image = specCaptcha.toBase64();
        redisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);

        return new CaptchaVo(image, key);
    }

    @Override
    public SystemUserInfoVo getSystemUserInfoById(Long userId) {
        SystemUser systemUser = systemUserService.getById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }

    @Override
    public String login(LoginVo loginVo) {
        //用算法计算一个密码方便测试:
     //   System.out.println(DigestUtils.sha256Hex("123456"));

        //1.判断是否输入了验证码
        if (!StringUtils.hasText(loginVo.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        //2.校验验证码
        String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCaptchaCode().toLowerCase())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //3.校验用户是否存在
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, loginVo.getUsername());
        SystemUser systemUser = systemUserMapper.selectOne(queryWrapper);
        if (systemUser == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //4.校验用户是否被禁
        if (systemUser.getStatus() == BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        String md5Password = DigestUtils.md5Hex(loginVo.getPassword());
        //5.校验用户密码
        if (!md5Password.equals(systemUser.getPassword())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //6.创建并返回TOKEN
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo info(String accountToken) {
        String username = ThreadLocalUtils.get();
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, username);
        SystemUser systemUser = systemUserService.getOne(queryWrapper);

        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;

    }
}
