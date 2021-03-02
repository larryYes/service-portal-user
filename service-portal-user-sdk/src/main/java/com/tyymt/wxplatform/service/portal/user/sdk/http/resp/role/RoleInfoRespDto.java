package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/2/3
 */
@Data
@ApiModel(description = "Role列表返回参数")
public class RoleInfoRespDto {

    @ApiModelProperty(value = "角色id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id")
    private String systemId;

    @ApiModelProperty(value = "子系统名")
    private String systemName;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色状态(0-启用 1-禁用)")
    private String enableFlag;
}
