package com.tyymt.wxplatform.service.portal.user.facade;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyymt.wxplatform.service.portal.user.common.dto.MenuTreeRespDto;
import com.tyymt.wxplatform.service.portal.user.common.model.*;
import com.tyymt.wxplatform.service.portal.user.common.service.*;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleSystemAuthReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleUpdateReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleInfoRespDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bestjoy.cloud.core.error.BusinessAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/14 14:55
 */

@Service
@RequiredArgsConstructor
public class SysRoleFacade {

    private final SysRoleService sysRoleService;

    private final SysUserRoleService sysUserRoleService;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysMenuService sysMenuService;

    /**
     * 校验角色名是否重复
     *
     * @param role
     * @return
     */
    @SneakyThrows
    private Boolean checkRoleName(SysRole role) {

        LambdaQueryWrapper<SysRole> query = Wrappers.<SysRole>query().lambda()
                .eq(SysRole::getRoleName, role.getRoleName())
                .eq(SysRole::getSystemId, role.getSystemId());
        // 当修改角色调用校验时需排除该角色本身
        if (role.getId() != null) {
            query.ne(SysRole::getId, role.getId());
        }
        List<SysRole> list = sysRoleService.list(query);
        return list.isEmpty();
    }

    /**
     * 检查该角色是否还有绑定关系
     *
     * @param roleId
     * @return
     */
    @SneakyThrows
    private Boolean checkRoleRelation(Integer roleId) {
        // 查询是否解除所有被赋予该角色权限的用户
        List<SysUserRole> urList = sysUserRoleService.list(
                Wrappers.<SysUserRole>query()
                        .lambda()
                        .eq(SysUserRole::getRoleId, roleId)
        );
        // 查询是否解除该角色与菜单的关系
        List<SysRoleMenu> rsmList = sysRoleMenuService.list(
                Wrappers.<SysRoleMenu>query()
                        .lambda()
                        .eq(SysRoleMenu::getRoleId, roleId)
        );

        if (urList.isEmpty()) {
            return rsmList.isEmpty();
        }
        // 角色名重复返回 false
        return false;
    }

    public int insertRole(RoleReqDto roleReqDto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleReqDto, role);

        //校验角色名是否重复
        if (!checkRoleName(role)) {
            BusinessAssert.throwIllegalArgumentException("与该系统中的角色命名重复");
        }
        return sysRoleService.insertRole(role);
    }

    public List<RoleInfoRespDto> selectRole(Integer roleId, String roleName, String systemId) {
        return sysRoleService.selectRole(roleId, roleName, systemId);
    }

    public int deleteByRoleId(Integer id) {
        // 检查该角色是否还有绑定关系
        if (!checkRoleRelation(id)) {
            BusinessAssert.throwIllegalArgumentException("请先解除被赋予该角色权限的用户或解除该角色与菜单的关系");
        }
        return sysRoleService.deleteByRoleId(id);
    }

    public boolean saveRoleMenuRelationShip(RoleSystemAuthReqDto rsAuthReqDto) {
        return sysRoleService.saveRoleMenuRelationShip(rsAuthReqDto.getRoleId(), rsAuthReqDto.getMenuId());
    }

    public boolean updateById(RoleUpdateReqDto roleUpDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleUpDto, sysRole);

        //校验角色名是否重复
        if (!checkRoleName(sysRole)) {
            BusinessAssert.throwIllegalArgumentException("与该系统中的角色命名重复");
        }
        return sysRoleService.updateById(sysRole);
    }

    public List<MenuTreeRespDto> getAuth(Integer roleId) {

        List<SysRoleMenu> rsmList = sysRoleMenuService.list(
                Wrappers.<SysRoleMenu>query()
                        .lambda()
                        .eq(SysRoleMenu::getRoleId, roleId)
        );
        if (rsmList.isEmpty()) {
            BusinessAssert.throwIllegalArgumentException("该角色未被赋予菜单权限");
        }
        List<Integer> menuIds = new ArrayList<>();
        for (SysRoleMenu menu : rsmList) {
            if (null != menu.getMenuId()) {
                menuIds.add(menu.getMenuId());
            }
        }
        List<SysMenu> authMenus = sysMenuService.listByIds(menuIds);

        // 将所有菜单权限List数据转化为树形结构
        List<MenuTreeRespDto> resp = new ArrayList<>();
        for (SysMenu menu : authMenus) {
            MenuTreeRespDto userAuthDto = new MenuTreeRespDto();
            // 如果没有上级目录
            if (!menuIds.contains(menu.getParentId())) {
                userAuthDto.setChildren(getChildren(authMenus, menu.getId()));
                menuCopyProperties(menu, userAuthDto);
                resp.add(userAuthDto);
            }
        }

        return resp;
    }

    public List<MenuTreeRespDto> getMenus(Integer roleId) {
        //根据角色查询所属系统的所有菜单
        SysRole role = sysRoleService.getById(roleId);
        List<SysMenu> menuList = sysMenuService.list(Wrappers
                .<SysMenu>query()
                .lambda()
                .eq(SysMenu::getSystemId, role.getSystemId())
        );
        List<MenuTreeRespDto> resp = new ArrayList<>();
        // 将所属系统所有菜单权限List数据转化为树形结构
        for (SysMenu menu : menuList) {
            //获取一级目录,无父级菜单则parentId默认为零
            MenuTreeRespDto userAuthDto = new MenuTreeRespDto();
            if (menu.getParentId().equals(0)) {
                userAuthDto.setChildren(getChildren(menuList, menu.getId()));
                menuCopyProperties(menu, userAuthDto);
                resp.add(userAuthDto);
            }
        }
        return resp;
    }

    /**
     * 递归查询
     *
     * @param subMenuList
     * @param menuId
     * @return
     */
    public List<MenuTreeRespDto> getChildren(List<SysMenu> subMenuList, Integer menuId) {
        // 获取下级标题
        List<MenuTreeRespDto> subTree = new ArrayList<>();
        for (SysMenu subMenu : subMenuList) {
            // 菜单ID等于下级菜单的parentID
            if (subMenu.getParentId().equals(menuId)) {
                MenuTreeRespDto menuTree = new MenuTreeRespDto();
                menuTree.setChildren(getChildren(subMenuList, subMenu.getId()));
                menuCopyProperties(subMenu, menuTree);
                subTree.add(menuTree);
            }
        }

        return subTree;
    }

    /**
     * 实体数据复制到返回体中
     *
     * @param dto
     * @param subMenu
     * @return
     */
    public MenuTreeRespDto menuCopyProperties(SysMenu subMenu, MenuTreeRespDto dto) {
        dto.setId(subMenu.getId());
        dto.setSystemId(subMenu.getSystemId());
        dto.setName(subMenu.getName());
        dto.setType(subMenu.getType());
        dto.setIcon(subMenu.getIcon());
        dto.setPath(subMenu.getPath());
        dto.setKeepAlive(subMenu.getKeepAlive());
        dto.setPermission(subMenu.getPermission());
        dto.setSort(subMenu.getSort());
        dto.setParentId(subMenu.getParentId());
        return dto;
    }
}
