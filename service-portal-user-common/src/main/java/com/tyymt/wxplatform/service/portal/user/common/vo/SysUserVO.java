package com.tyymt.wxplatform.service.portal.user.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangfei
 * @create 2020-10-14 16:15
 */
@Data
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "所属部门id")
    private Integer deptId;

    @ApiModelProperty(value = "人员状态(0-启用 1-禁用)")
    private String enableFlag;
}
