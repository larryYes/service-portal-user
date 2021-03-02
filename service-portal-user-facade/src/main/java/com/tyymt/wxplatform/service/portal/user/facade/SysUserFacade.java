package com.tyymt.wxplatform.service.portal.user.facade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyymt.wxplatform.service.portal.user.common.constant.CommonConstants;
import com.tyymt.wxplatform.service.portal.user.common.dto.MenuTreeRespDto;
import com.tyymt.wxplatform.service.portal.user.common.dto.SysUserDTO;
import com.tyymt.wxplatform.service.portal.user.common.dto.UserInfoDTO;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUserRole;
import com.tyymt.wxplatform.service.portal.user.common.service.SysMenuService;
import com.tyymt.wxplatform.service.portal.user.common.service.SysSystemService;
import com.tyymt.wxplatform.service.portal.user.common.service.SysUserRoleService;
import com.tyymt.wxplatform.service.portal.user.common.service.SysUserService;
import com.tyymt.wxplatform.service.portal.user.common.utils.StringFilterUtil;
import com.tyymt.wxplatform.service.portal.user.common.vo.SysUserVO;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.user.UserRoleAuthReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu.MenuInfoRespDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.role.RoleRespDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.system.SystemRoleRespDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.BusinessException;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.tyymt.wxplatform.service.portal.user.common.error.Error.Biz.RECORDS_NOT_FOUND;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Service
@RequiredArgsConstructor
public class SysUserFacade {

    private final SysUserService sysUserService;

    private final SysSystemService systemService;

    private final SysUserRoleService sysUserRoleService;

    private final SysMenuService sysMenuService;

    final BASE64Decoder decoder = new BASE64Decoder();

    public IPage getUserWithRolePage(PageReqDto request, SysUserVO vo) {

        vo.setNickName(StringUtils.trim(vo.getNickName()));
        vo.setUserName(StringUtils.trim(vo.getUserName()));
        vo.setUserName(StringFilterUtil.StringFilter(vo.getUserName()));
        vo.setNickName(StringFilterUtil.StringFilter(vo.getNickName()));
        Page page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserService.getUserVosPage(page, vo);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateById(SysUserDTO sysUser) {
        SysUser user = sysUserService.getById(sysUser.getId());
        BusinessAssert.notNull(user, ErrorCodeAndMessage.create(9999981, "记录已删除，请刷新！"));
        sysUser.setUpdateTime(LocalDateTime.now());
        return sysUserService.updateById(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveUser(SysUserDTO sysUser) {

        SysUser entity = sysUserService.getOne(
                Wrappers.<SysUser>query()
                        .lambda()
                        .eq(SysUser::getUserName, sysUser.getUserName())
        );
        BusinessAssert.isNull(entity, ErrorCodeAndMessage.create(9999981, "该用户名已存在"));
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setCreateTime(LocalDateTime.now());
        //获取用户id
        return sysUserService.saveReturnId(sysUser);
    }

    public List<SysUserDTO> getUserWithRole(SysUserVO vo) {
        return sysUserService.getUserList(vo);
    }

    public List<SysSystem> findSystemByUserId(Integer userId) {
        return systemService.findSystemByUserId(userId);
    }

    public SysUserDTO getByUserName(String userName) {
        return sysUserService.getUserByUserName(userName);
    }

    public SysUserDTO getUserInfo(Integer userId) {
        SysUser one = sysUserService.getOne(
                Wrappers.<SysUser>query()
                        .lambda()
                        .eq(SysUser::getId, userId)
        );
        // 查询该用户所属的角色权限
        List<RoleRespDto> roleAuth = sysUserService.getRoleAuth(userId);

        if (one != null) {
            SysUserDTO dto = new SysUserDTO();
            BeanUtils.copyProperties(one, dto);
            List<SysSystem> systemByUserId = systemService.findSystemByUserId(userId);
            dto.setSystemList(systemByUserId);
            dto.setPassword(null);
            dto.setRoleList(roleAuth);
            return dto;
        }
        throw new BusinessException(RECORDS_NOT_FOUND);
    }

    public Boolean removeById(Integer id) {
        return sysUserService.removeById(id);
    }

    /**
     * 更新用户密码
     *
     * @param sysUser
     * @return
     */
    @SneakyThrows
    public Boolean updatePassword(SysUserDTO sysUser) {
        String password = new String(decoder.decodeBuffer(sysUser.getPassword()), "UTF-8");
        sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        return sysUserService.updateById(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserRoleRelationShip(UserRoleAuthReqDto urAuthReqDto) {

        // 分配权限前先删除关系表中该角色下的所有权限
        sysUserRoleService.remove(
                Wrappers.<SysUserRole>query()
                        .lambda()
                        .eq(SysUserRole::getUserId, urAuthReqDto.getUserId())
        );

        List<SysUserRole> list = new ArrayList<>();

        // 遍历角色ID数组
        for (Integer roleId : urAuthReqDto.getRoleId()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(urAuthReqDto.getUserId());
            userRole.setRoleId(roleId);
            list.add(userRole);
        }
        return sysUserRoleService.saveBatch(list);
    }

    /**
     * 查询该用户在此系统中的所有权限
     *
     * @param userId
     * @param systemCode
     * @return 用户信息和树形结构权限信息
     */
    public UserInfoDTO getAuth(Integer userId, String systemCode) {
        BusinessAssert.notNull(userId, ErrorCodeAndMessage.create(9999981, "用户id不能为空"));

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        SysUser userInfo = sysUserService.getById(userId);
        userInfoDTO.setUserInfo(userInfo);
        // 查询该用户被分配的所有菜单（按钮）数据
        List<SysMenu> menuAuthList = sysUserService.getUserAuth(userId, systemCode);

        List<MenuTreeRespDto> resp = new ArrayList<>();
        // 将所有菜单权限List数据转化为树形结构
        for (SysMenu menu : menuAuthList) {
            //获取一级目录,无父级菜单则parentId默认为零
            MenuTreeRespDto userAuthDto = new MenuTreeRespDto();
            if (menu.getParentId().equals(0)) {
                userAuthDto.setChildren(getChildren(menuAuthList, menu.getId()));
                menuCopyProperties(userAuthDto, menu);
                resp.add(userAuthDto);
            }
        }
        userInfoDTO.setPermissions(resp);
        return userInfoDTO;
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
                menuCopyProperties(menuTree, subMenu);
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
    public MenuTreeRespDto menuCopyProperties(MenuTreeRespDto dto, SysMenu subMenu) {
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