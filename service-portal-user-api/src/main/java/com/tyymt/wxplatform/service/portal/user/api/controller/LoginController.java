package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.tyymt.wxplatform.service.portal.user.common.constant.CommonConstants;
import com.tyymt.wxplatform.service.portal.user.common.dto.SysUserDTO;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.facade.SysUserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangfei
 * @create 2020-10-28 15:59
 */
@Api(value = "login", tags = "登录")
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class LoginController {
    final BASE64Decoder decoder = new BASE64Decoder();
    private final SysUserFacade sysUserFacade;

    @SneakyThrows
    @PostMapping(value = "/auth/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    public Result login(@RequestBody SysUser sysUser) {

        BusinessAssert.notBlank(sysUser.getUserName(), ErrorCodeAndMessage.create(9999981, "用户名不能为空"));
        BusinessAssert.notBlank(sysUser.getPassword(), ErrorCodeAndMessage.create(9999981, "密码不能为空"));
        SysUserDTO entity = sysUserFacade.getByUserName(sysUser.getUserName());
        String password = new String(decoder.decodeBuffer(sysUser.getPassword()), "UTF-8");
        if (entity != null) {
            if (CommonConstants.STATUS_DISABLED.equals(entity.getEnableFlag())) {
                return Result.fail(9999999, "人员已禁用，请联系管理员");
            }
            if (new BCryptPasswordEncoder().matches(password, entity.getPassword())) {
                entity.setPassword(null);
                Map<String, Object> map = new HashMap<>(16);
                map.put("userInfo", entity);
                return Result.success(map);
            } else {
                return Result.fail(9999999, "用户名或密码不正确");
            }
        }
        return Result.fail(9999999, "用户名或密码不正确");
    }

}
