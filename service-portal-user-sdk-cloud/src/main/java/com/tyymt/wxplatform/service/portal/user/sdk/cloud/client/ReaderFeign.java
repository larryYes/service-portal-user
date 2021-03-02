package com.tyymt.wxplatform.service.portal.user.sdk.cloud.client;

import com.tyymt.wxplatform.service.portal.user.sdk.cloud.config.FeignConfig;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.ReadInfoByNameReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.ReadInfoReqDto;
import net.bestjoy.cloud.core.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

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
        value = "service-portal-user",
        path = "/service-portal-user/reader",
        configuration = FeignConfig.class,
        contextId = "service-portal-user-reader",
        url = "${service-portal-user.url.remoteUrl:}"
)
public interface ReaderFeign {

    @GetMapping(value = "/page")
    Result page(@SpringQueryMap PageReqDto req);

    @GetMapping(value = "/selectInfo")
    Result selectInfo(@SpringQueryMap ReadInfoReqDto req);

    @GetMapping(value = "/selectInfoByName")
    Result selectInfoByName(@SpringQueryMap ReadInfoByNameReqDto req);
}
