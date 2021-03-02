package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.tyymt.wxplatform.service.portal.user.facade.SysMenuFacade;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu.MenuReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.menu.MenuUpdateReqDto;
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
 * @Description TODO
 * @Date 21/1/15
 */

@Api(value = "menu", tags = "菜单管理模块")
@RestController
@RequestMapping(value = "/menu")
public class SysMenuController {

    @Resource
    private SysMenuFacade sysMenuFacade;

    @ApiOperation(value = "添加菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping
    public Result add(
            @ApiParam(name = "menuReqDto ", value = "插入菜单接口入参", required = true)
            @RequestBody MenuReqDto menuReqDto) {
        BusinessAssert.notNull(menuReqDto.getSystemId(), ErrorCodeAndMessage.create(9999981, "子系统id不能为空"));
        return Result.success(sysMenuFacade.insertMenu(menuReqDto));
    }

    @ApiOperation(value = "根据ID删除菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "菜单ID", required = true)
            @PathVariable Integer id) {
        BusinessAssert.notNull(id, ErrorCodeAndMessage.create(9999981, "菜单id不能为空"));
        return Result.success(sysMenuFacade.deleteByMenuId(id));
    }

    @ApiOperation(value = "根据ID更新菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "PUT")
    @PutMapping
    public Result update(
            @ApiParam(name = "menuDto", value = "插入菜单接口入参", required = true)
            @RequestBody MenuUpdateReqDto menuDto) {
        BusinessAssert.notNull(menuDto.getId(), ErrorCodeAndMessage.create(9999981, "菜单id不能为空"));
        return Result.success(sysMenuFacade.updateByMenuId(menuDto));
    }

    @ApiOperation(value = "条件查询菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/list")
    public Result select(
            @ApiParam(name = "systemId", value = "系统Id", required = false)
            @RequestParam(required = false) Integer systemId,
            @ApiParam(name = "systemName", value = "系统名", required = false)
            @RequestParam(required = false) String systemName,
            @ApiParam(name = "menuName", value = "菜单名", required = false)
            @RequestParam(required = false) String menuName    ) {
        return Result.success(sysMenuFacade.selectMenuByPage(systemId,menuName,systemName));
    }

    @ApiOperation(value = "根据菜单ID查询菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/{id}")
    public Result selectById(
            @ApiParam(name = "id", value = "菜单ID", required = true)
            @PathVariable Integer id) {
        return Result.success(sysMenuFacade.selectById(id));
    }

}
