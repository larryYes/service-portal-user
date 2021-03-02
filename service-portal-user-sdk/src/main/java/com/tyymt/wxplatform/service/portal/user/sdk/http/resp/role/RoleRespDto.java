package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/25
 */
@Data
@ApiModel(description = "Role查询返回参数")
public class RoleRespDto {
    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;
}
