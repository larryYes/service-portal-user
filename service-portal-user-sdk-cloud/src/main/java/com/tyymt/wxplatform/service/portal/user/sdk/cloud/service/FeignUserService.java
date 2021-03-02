package com.tyymt.wxplatform.service.portal.user.sdk.cloud.service;

import com.alibaba.fastjson.JSON;
import com.tyymt.wxplatform.service.portal.user.sdk.cloud.client.UserFeign;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.user.UserReqDto;
import lombok.Setter;
import net.bestjoy.cloud.core.bean.Result;


/**
 * 不同于springMVC中的service，这里的FeignService用户服务交互
 * 服务间的交互不直接使用feign，而是使用这里的service
 * 主要在这里做返回体的转换和异常处理
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-23
 */
public class FeignUserService {

    @Setter
    private UserFeign userFeign;

    public Boolean add(UserReqDto req) {
        Result result = userFeign.add(req);
        //异常处理
        result.checkErrorResponse();

        //返回体的转换
        return JSON.parseObject(JSON.toJSONString(result.getResult()), Boolean.class);
    }

}
