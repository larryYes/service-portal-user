package com.tyymt.wxplatform.service.portal.user.sdk.cloud.config;

import com.tyymt.wxplatform.service.portal.user.sdk.cloud.service.FeignUserService;
import com.tyymt.wxplatform.service.portal.user.sdk.cloud.client.UserFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 注入service
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-23
 */
@Configuration
public class ServiceConfig {

    @Resource
    private UserFeign userFeign;

    @Bean(name = "feignUserService")
    public FeignUserService feignUserService(){
        FeignUserService service = new FeignUserService();
        service.setUserFeign(userFeign);
        return service;
    }
}
