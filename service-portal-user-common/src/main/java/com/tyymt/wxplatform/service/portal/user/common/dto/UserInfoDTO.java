package com.tyymt.wxplatform.service.portal.user.common.dto;

import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author huangfei
 * @create 2021-02-01
 */
@Data
public class UserInfoDTO {
    /**
     * 用户信息
     */
    private SysUser userInfo;

    /**
     * 用户权限
     */
    private List<MenuTreeRespDto> permissions;

}
