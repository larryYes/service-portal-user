package com.tyymt.wxplatform.service.portal.user.sdk.http.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/18
 */

@Data
@ApiModel(description = "用户分配角色权限入参")
public class UserRoleAuthReqDto {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "角色id")
    private Integer[] roleId;
}
