package com.atguigu.lease.web.admin.controller.login;


import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.context.LoginUserContext;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
    * @Description:
     *
     * http://www.yzcopen.com/img/imgbase64
    * @Param: []
    * @return: com.atguigu.lease.common.result.Result<com.atguigu.lease.web.admin.vo.login.CaptchaVo>
    * @Author: simonf
    * @Date: 2024/1/25
    */
    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captcha = loginService.getCaptcha();
        return Result.ok(captcha);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token = loginService.login(loginVo);
        return Result.ok(token);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info(@RequestHeader("access_token")String accountToken) {

        if (StringUtils.isBlank(accountToken)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        SystemUserInfoVo systemUserInfoVo = loginService.info(accountToken);

        return Result.ok(systemUserInfoVo);
    }
}