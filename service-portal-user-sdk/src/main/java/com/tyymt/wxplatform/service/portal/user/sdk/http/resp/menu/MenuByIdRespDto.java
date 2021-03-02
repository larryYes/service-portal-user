package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/2/24
 */
@Data
@ApiModel(description = "根据ID查询菜单返参")
public class MenuByIdRespDto {
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id" )
    private String systemId;

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
    private String keepAlive;

    @ApiModelProperty(value = "菜单类型 （0目录 1菜单 2按钮）")
    private String type;
}
