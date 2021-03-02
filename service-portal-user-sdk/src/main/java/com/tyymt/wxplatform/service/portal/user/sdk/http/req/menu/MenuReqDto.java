package com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */
@Data
@ApiModel(description = "插入菜单接口入参")
public class MenuReqDto {

    @ApiModelProperty(value = "子系统Id")
    private Integer systemId;

    @ApiModelProperty(value= "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "是否启用：0-开启，1- 关闭")
    private Integer keepAlive;

    @ApiModelProperty(value = "菜单类型 （0目录 1菜单 2按钮）")
    private Integer type;

}
