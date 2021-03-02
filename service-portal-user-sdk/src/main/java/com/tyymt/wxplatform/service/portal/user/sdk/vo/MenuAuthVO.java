package com.tyymt.wxplatform.service.portal.user.sdk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/20
 */
@Data
public class MenuAuthVO {

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;
}
