package com.tyymt.wxplatform.service.portal.user.sdk.cloud.config;

import feign.Feign;
import feign.Retryer;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 服务配置类，开启feign
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-23
 */
@Configuration
@EnableFeignClients(basePackages = {"com.tyymt.wxplatform.service.portal.user.sdk.cloud.client"})
public class FeignConfig {

    /**
     * 替换解析queryMap的类，实现父类中变量的映射
     * Bean的name一定要全局唯一
     *
     * @return
     */
    @Bean(name = "service-portal-user-feignBuilder")
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .queryMapEncoder(new BeanQueryMapEncoder())
                .retryer(Retryer.NEVER_RETRY);
    }

}
