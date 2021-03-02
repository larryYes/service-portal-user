package com.tyymt.wxplatform.service.portal.user.sdk.cloud.client;

import com.tyymt.wxplatform.service.portal.user.sdk.cloud.config.FeignConfig;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.user.UserReqDto;
import net.bestjoy.cloud.core.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Reader接口Feign
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-23
 */

/**
 * value对应的是服务端配置文件中的spring.application.name，不分大小写
 * path需要相对应该controller的url
 * configuration用于指定配置文件
 * contextId 上下文id，必须要保证全局唯一
 * url 服务的url，用于当spring.profiles.include为discovery-local时，服务调用不走注册中心，而是通过url进行访问
 */
@FeignClient(
        value = "service-user",
        path = "/service-user/user",
        configuration = FeignConfig.class,
        contextId = "service-user-user",
        url = "${service-user.url.remoteUrl:}"
)
public interface UserFeign {
    @PostMapping("/add")
    Result add(@RequestBody UserReqDto req);
}
