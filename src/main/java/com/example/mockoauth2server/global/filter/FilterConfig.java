package com.example.mockoauth2server.global.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class FilterConfig {

    @Value("${temporary.auth.header}")
    private String temporaryAuthHeader;

    @Bean
    public FilterRegistrationBean<HttpMessageLoggingFilter> httpMessageLoggingFilterRegistrationBean() {
        FilterRegistrationBean<HttpMessageLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        HttpMessageLoggingFilter filter = new HttpMessageLoggingFilter(temporaryAuthHeader);
        registrationBean.setFilter(filter);
        registrationBean.setOrder(-112);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
