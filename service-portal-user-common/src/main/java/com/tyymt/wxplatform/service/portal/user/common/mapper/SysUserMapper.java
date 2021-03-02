package com.tyymt.wxplatform.service.portal.user.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyymt.wxplatform.service.portal.user.common.dto.SysUserDTO;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.common.vo.SysUserVO;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleRespDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页获取用户信息
     *
     * @param page
     * @return
     */
    IPage<List<SysUserDTO>> getUserVosPage(Page page, @Param("query") SysUserVO sysUserVO);

    int saveReturnId(SysUser sysUser);

    /**
     * 获取用户信息列表
     * @param vo
     * @return
     */
    List<SysUserDTO> getUserList(@Param("query") SysUserVO vo);

    SysUserDTO getUserByUserName(String userName);

    /**
     * 查询用户所在系统的所有权限
     * @param userId
     * @param systemCode
     * @return
     */
    List<SysMenu> getUserAuth(Integer userId, String systemCode);

    /**
     * 根据用户ID查询所属角色权限
     * @param userId
     * @return
     */
    List<RoleRespDto> getRoleAuth(Integer userId);

}
