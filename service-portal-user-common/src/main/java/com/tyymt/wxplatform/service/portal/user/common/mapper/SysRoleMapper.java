package com.tyymt.wxplatform.service.portal.user.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysRole;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleInfoRespDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/14
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<RoleInfoRespDto> selectRole(Integer roleId, String roleName,String systemId);
}
