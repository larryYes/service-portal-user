package com.tyymt.wxplatform.service.portal.user.sdk.http.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description 角色
 * @Date 21/1/14 15:09
 */
@Data
@ApiModel(description = "插入角色接口入参")
public class RoleReqDto {

    @ApiModelProperty(value = "子系统Id",required = true)
    private Integer systemId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "是否启用(0-启用,1-禁用)")
    private String enableFlag;
}
