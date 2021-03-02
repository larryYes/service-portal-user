package com.tyymt.wxplatform.service.portal.user.sdk.http.req.role;

import com.tyymt.wxplatform.service.portal.user.sdk.vo.SystemMenuAuthVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */

@Data
@ApiModel(description = "分配角色菜单权限接口入参")
public class RoleSystemAuthReqDto {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID",required = true)
    private List<Integer> menuId;
 }
