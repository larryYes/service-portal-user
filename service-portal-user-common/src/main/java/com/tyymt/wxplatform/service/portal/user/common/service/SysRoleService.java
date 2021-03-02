package com.tyymt.wxplatform.service.portal.user.common.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyymt.wxplatform.service.portal.user.common.mapper.SysRoleMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysRole;
import com.tyymt.wxplatform.service.portal.user.common.model.SysRoleMenu;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleInfoRespDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/14 14:30
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleMenuService roleMenuService;

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    public int insertRole(SysRole role) {
        return roleMapper.insert(role);
    }

    public int deleteByRoleId(Integer id) {
        return roleMapper.deleteById(id);
    }

    /**
     * 给角色分配子系统、菜单权限
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenuRelationShip(Integer roleId, List<Integer> menuId) {

        // 分配权限前先删除关系表中该角色下的所有权限
        roleMenuService.remove(Wrappers
                .<SysRoleMenu>query()
                .lambda()
                .eq(SysRoleMenu::getRoleId,roleId)
        );

        //TODO:校验只能添加所属系统的菜单权限

        List<SysRoleMenu> roleMenus = new ArrayList<>();
        //子系统权限循环
        for (Integer mId : menuId) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(mId);
                roleMenus.add(roleMenu);
        }
        return roleMenuService.saveBatch(roleMenus);
    }

    public List<RoleInfoRespDto> selectRole(Integer roleId, String roleName, String systemId) {
        return roleMapper.selectRole(roleId,roleName,systemId);
    }
}
