package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu;

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
@ApiModel(description = "条件查询菜单返参")
public class MenuInfoRespDto {
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @ApiModelProperty(value = "子系统id")
    private String systemId;

    @ApiModelProperty(value = "子系统名")
    private String systemName;

    @ApiModelProperty(value = "菜单名称")
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
