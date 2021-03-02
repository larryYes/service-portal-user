package com.tyymt.wxplatform.service.portal.user.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyymt.wxplatform.service.portal.user.common.dto.SysUserDTO;
import com.tyymt.wxplatform.service.portal.user.common.mapper.SysUserMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.model.SysRole;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.common.vo.SysUserVO;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleRespDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-14 16:11
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    @Resource
    private SysUserMapper mapper;

    public IPage<List<SysUserDTO>> getUserVosPage(Page page, SysUserVO vo) {
        return mapper.getUserVosPage(page, vo);
    }

    public int saveReturnId(SysUser sysUser) {
        return mapper.saveReturnId(sysUser);
    }

    public List<SysUserDTO> getUserList(SysUserVO vo) {
        return mapper.getUserList(vo);
    }

    public SysUserDTO getUserByUserName(String userName) {
        return mapper.getUserByUserName(userName);
    }

    public List<SysMenu> getUserAuth(Integer userId, String systemCode) {
        return mapper.getUserAuth(userId,systemCode);
    }

    public List<RoleRespDto> getRoleAuth(Integer userId){
        return mapper.getRoleAuth(userId);
    }
}
