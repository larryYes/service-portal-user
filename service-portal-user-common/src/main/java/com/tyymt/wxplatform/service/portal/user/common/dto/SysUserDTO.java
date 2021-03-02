package com.tyymt.wxplatform.service.portal.user.common.dto;

import com.tyymt.wxplatform.service.portal.user.common.model.SysRole;
import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleRespDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-14 16:15
 */
@Data
public class SysUserDTO extends SysUser {
    /**
     * 系统列表
     */
    @ApiModelProperty(value = "系统列表")
    private List<SysSystem> systemList;

    /**
     * liugji：返回角色列表
     */
    @ApiModelProperty(value = "角色列表")
    private List<RoleRespDto> roleList;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String deptName;
}
