package com.atguigu.lease.web.app.custom.config;

import com.atguigu.lease.web.app.custom.converter.StringToBaseEnumConverterFactory;
import com.atguigu.lease.web.app.custom.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: smionf
 * @Date: 2024/01/27/14:11
 * @Description:
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${app.auth.path-patterns.include}")
    private String[] includePathPatterns;

    @Value("${app.auth.path-patterns.exclude}")
    private String[] excludePathPatterns;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(this.stringToBaseEnumConverterFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authenticationInterceptor).addPathPatterns(includePathPatterns).excludePathPatterns(excludePathPatterns);
    }
}