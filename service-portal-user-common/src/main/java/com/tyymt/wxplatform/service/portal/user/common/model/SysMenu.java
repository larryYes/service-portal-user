package com.tyymt.wxplatform.service.portal.user.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysRole> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @ApiModelProperty(value = "子系统Id" )
    private Integer systemId;

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @TableLogic
    private String delFlag;
}
