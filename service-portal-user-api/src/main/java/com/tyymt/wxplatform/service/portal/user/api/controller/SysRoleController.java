package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.tyymt.wxplatform.service.portal.user.facade.SysRoleFacade;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleSystemAuthReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.role.RoleUpdateReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author by liuguangjin
 * @Description 角色
 * @Date 21/1/14 14:38
 */
@Api(value = "role", tags = "角色管理模块")
@RestController
@RequestMapping(value = "/role")
public class SysRoleController {

    @Resource
    private SysRoleFacade sysRoleFacade;

    @ApiOperation(value = "添加角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping
    public Result add(
            @ApiParam(name = "RoleReqDto", value = "插入角色接口入参", required = true)
            @RequestBody RoleReqDto roleReqDto) {
        BusinessAssert.notNull(roleReqDto.getRoleName(), ErrorCodeAndMessage.create(9999981, "角色名不能为空"));
        BusinessAssert.notNull(roleReqDto.getSystemId(), ErrorCodeAndMessage.create(9999981, "子系统ID不能为空"));
        return Result.success(sysRoleFacade.insertRole(roleReqDto));
    }

    @ApiOperation(value = "条件查询角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/page")
    public Result selectRole(
            @ApiParam(name = "roleId", value = "角色ID", required = false)
            @RequestParam(required = false) Integer roleId,
            @ApiParam(name = "roleName", value = "角色名", required = false)
            @RequestParam(required = false) String roleName,
            @ApiParam(name = "systemId",value = "子系统ID",required = false)
            @RequestParam(required = false) String systemId
            ) {
        return Result.success(sysRoleFacade.selectRole(roleId, roleName, systemId));
    }

    @ApiOperation(value = "根据ID删除角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "角色ID", required = true)
            @PathVariable Integer id) {
        BusinessAssert.notNull(id, ErrorCodeAndMessage.create(9999981, "角色id不能为空"));
        return Result.success(sysRoleFacade.deleteByRoleId(id));
    }

    @ApiOperation(value = "根据ID更新角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "PUT")
    @PutMapping
    public Result update(
            @ApiParam(name = "RoleUpdateReqDto", value = "更新角色接口入参", required = true)
            @RequestBody RoleUpdateReqDto roleUpDto) {
        BusinessAssert.notNull(roleUpDto.getId(), ErrorCodeAndMessage.create(9999981, "角色id不能为空"));
        BusinessAssert.notNull(roleUpDto.getSystemId(), ErrorCodeAndMessage.create(9999981, "系统ID不能为空，需要用于校验角色名是否重复"));
        return Result.success(sysRoleFacade.updateById(roleUpDto));
    }

    @ApiOperation(value = "给角色分配菜单权限", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/auth")
    public Result doAssign(
            @ApiParam(name = "RoleSystemAuthReqDto", value = "分配角色菜单权限接口入参", required = true)
            @RequestBody RoleSystemAuthReqDto roleSystemAuthReqDto) {
        BusinessAssert.notNull(roleSystemAuthReqDto.getRoleId(), ErrorCodeAndMessage.create(9999981, "角色id不能为空"));
        BusinessAssert.notNull(roleSystemAuthReqDto.getMenuId(), ErrorCodeAndMessage.create(9999981, "菜单id不能为空"));
        return Result.success(sysRoleFacade.saveRoleMenuRelationShip(roleSystemAuthReqDto));
    }

    @ApiOperation(value = "根据角色ID查询已经被赋予的所有菜单权限", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/{id}/auth")
    public Result getAuth(
            @ApiParam(name = "id", value = "角色ID", required = true)
            @PathVariable Integer id) {
        return Result.success(sysRoleFacade.getAuth(id));
    }

    @ApiOperation(value= "根据角色ID所属的系统查询所有可授权的菜单列表",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/{id}/menus")
    public Result getMenus(
            @ApiParam(name = "id", value = "角色ID", required = true)
            @PathVariable Integer id
    ){
        return Result.success(sysRoleFacade.getMenus(id));
    }
}
