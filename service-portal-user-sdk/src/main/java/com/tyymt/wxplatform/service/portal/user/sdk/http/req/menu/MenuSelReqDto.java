package com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/18
 */
@Data
@ApiModel(description = "菜单条件查询接口入参")
public class MenuSelReqDto {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id")
    private Integer systemId;

    @ApiModelProperty(value= "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "是否启用：0-开启，1- 关闭")
    private Integer keepAlive;

    @ApiModelProperty(value = "菜单类型 （0目录 1菜单 2按钮）")
    private Integer type;

}
