package com.tyymt.wxplatform.service.portal.user.sdk.http.req.role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */

@Data
@ApiModel(description = "更新角色接口入参")
public class RoleUpdateReqDto {

    @ApiModelProperty(value = "角色id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id",required = true)
    private Integer systemId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色状态(0-启用 1-禁用)")
    private String enableFlag;
}
