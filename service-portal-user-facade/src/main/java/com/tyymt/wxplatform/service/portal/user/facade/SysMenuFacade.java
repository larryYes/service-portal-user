package com.tyymt.wxplatform.service.portal.user.facade;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyymt.wxplatform.service.portal.user.common.constant.MenuType;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.service.SysMenuService;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu.MenuReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu.MenuUpdateReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu.MenuByIdRespDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu.MenuInfoRespDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bestjoy.cloud.core.error.BusinessAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */

@Service
@RequiredArgsConstructor
public class SysMenuFacade {

    private final SysMenuService sysMenuService;

    @SneakyThrows
    private void checkMenu(SysMenu sysMenu) {
        // 目录的上级校验
        if (sysMenu.getType().equals(MenuType.CATALOGUE)) {
            SysMenu menu = sysMenuService.getById(sysMenu.getParentId());
            if (menu != null && menu.getType().equals(MenuType.MENU)) {
                BusinessAssert.throwIllegalArgumentException("目录的上级不能是菜单类型");
            }
        }
        // 菜单的上级校验
        if (sysMenu.getType().equals(MenuType.MENU)) {
            SysMenu menu = sysMenuService.getById(sysMenu.getParentId());
            if (menu != null && menu.getType().equals(MenuType.MENU)) {
                BusinessAssert.throwIllegalArgumentException("菜单的上级不能是菜单类型");
            }
        }
        // 按钮的上级校验
        if (sysMenu.getType().equals(MenuType.BUTTON) && sysMenu.getParentId() == 0) {
            BusinessAssert.throwIllegalArgumentException("请给按钮添加上级菜单");
        }
        if (sysMenu.getParentId().equals(sysMenu.getId())) {
            BusinessAssert.throwIllegalArgumentException("上级菜单不能作为该菜单本身");
        }
    }

    public int insertMenu(MenuReqDto menuReqDto) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menuReqDto, sysMenu);
        // 若无上级ID则默认设置为0
        if (StringUtils.isEmpty(sysMenu.getParentId())) {
            sysMenu.setParentId(0);
        }
        // 菜单数据校验
        checkMenu(sysMenu);
        return sysMenuService.insertMenu(sysMenu);
    }

    public boolean updateByMenuId(MenuUpdateReqDto menuDto) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menuDto, sysMenu);
        if (StringUtils.isEmpty(sysMenu.getParentId())) {
            sysMenu.setParentId(0);
        }
        // 菜单数据校验
        checkMenu(sysMenu);
        return sysMenuService.updateById(sysMenu);
    }

    public int deleteByMenuId(Integer menuId) {
        //判断是否有子菜单
        List<SysMenu> list = sysMenuService.list(
                Wrappers.<SysMenu>query()
                        .lambda()
                        .eq(SysMenu::getParentId, menuId)
        );
        if (!list.isEmpty()) {
            BusinessAssert.throwIllegalArgumentException("请先删除下级菜单");
        }
        return sysMenuService.deleteByMenuId(menuId);
    }

    /**
     * 菜单列表展示
     *
     * @param menuName
     * @param systemName
     * @return
     */
    public List<MenuInfoRespDto> selectMenuByPage(Integer systemId, String menuName, String systemName) {
        // TODO是否需要传子系统代码
        List<MenuInfoRespDto> allMenus = sysMenuService.selectMenu(systemId, systemName);

        List<MenuInfoRespDto> respMenus = new ArrayList<>();
        // 根据菜单名查询，需要查询所属的上下级菜单并返回
        if (!StringUtils.isEmpty(menuName)) {
            // 将所有匹配到的菜单添加到List集合中
            for (MenuInfoRespDto menus : allMenus) {
                if (menus.getName().contains(menuName)) {
                    respMenus.add(menus);
                }
            }
            // 获取上下级菜单
            for (MenuInfoRespDto allMenu :
                    allMenus) {
                respMenus.addAll(getSupAndSub(allMenu, respMenus));
            }
            // 如果没有菜单查询条件则全部返回
        } else {
            respMenus.addAll(allMenus);
        }
        return respMenus;
    }

    /**
     * 获取上下级菜单
     *
     * @param allMenu
     * @param respMenus
     * @return
     */
    public HashSet<MenuInfoRespDto> getSupAndSub(MenuInfoRespDto allMenu, List<MenuInfoRespDto> respMenus) {
        HashSet<MenuInfoRespDto> listMenu = new HashSet<>();
        for (MenuInfoRespDto menuPar : respMenus) {
            // 已经存在该菜单则跳过此方法
            if (allMenu.getId().equals(menuPar.getId())) {
                return listMenu;
            }
        }
        for (MenuInfoRespDto menuPar : respMenus) {
            // 判断是否是上下级菜单
            if (menuPar.getParentId().equals(allMenu.getId()) || menuPar.getId().equals(allMenu.getParentId())) {
                listMenu.add(allMenu);
            }
        }
        return listMenu;
    }

    public MenuByIdRespDto selectById(Integer menuId) {
        SysMenu menu = sysMenuService.getById(menuId);
        MenuByIdRespDto menuInfo = new MenuByIdRespDto();
        BeanUtils.copyProperties(menu, menuInfo);
        menuInfo.setKeepAlive(menu.getKeepAlive().toString());
        menuInfo.setType(menu.getType().toString());
        menuInfo.setSystemId(menu.getSystemId().toString());
        return menuInfo;
    }
}
