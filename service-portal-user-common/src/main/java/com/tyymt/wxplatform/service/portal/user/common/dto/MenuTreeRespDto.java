package com.tyymt.wxplatform.service.portal.user.common.dto;

import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/28
 */
@Data
@ApiModel(description = "树形结构返回菜单列表实体类")
public class MenuTreeRespDto {
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id")
    private Integer systemId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "父菜单ID(无上级菜单则默认为0)")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "是否启用：0-开启，1- 关闭")
    private Integer keepAlive;

    @ApiModelProperty(value = "菜单类型 （0目录 1菜单 2按钮）")
    private Integer type;

    List<MenuTreeRespDto> children = new ArrayList<>();
}
