package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.userRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/19
 */
@Data
@ApiModel(value = "用户角色权限返回体")
public class UserRoleRespDto {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "角色名")
    private List<String> roleName;
}
