package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.common.utils.RegexUtil;
import com.tyymt.wxplatform.service.portal.user.facade.SysSystemFacade;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangfei
 * @create 2020-10-27 15:58
 */
@Api(value = "system", tags = "系统管理模块")
@RestController
@RequestMapping(value = "/system")
@RequiredArgsConstructor
public class SysSystemController {

    private final SysSystemFacade systemFacade;

    @ApiOperation(value = "分页获取系统信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/page")
    public Result getSystemPage(PageReqDto req, SysSystem system) {
        BusinessAssert.notNull(req.getCurrent(), ErrorCodeAndMessage.create(9999981, "当前页码不能为空"));
        BusinessAssert.notNull(req.getSize(), ErrorCodeAndMessage.create(9999981, "页码大小不能为空"));
        return Result.success(systemFacade.page(req, system));
    }

    @ApiOperation(value = "条件获取子系统以及所拥有的所有角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/list")
    public Result getSystemList(
            @ApiParam(required = false,name = "id",value ="用户ID")
            @RequestParam(required = false) Integer id) {
        return Result.success(systemFacade.getSystemList(id));
    }

    @ApiOperation(value = "系统图片上传", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/upload")
    @ResponseBody
    public Result uploadImage(@RequestPart MultipartFile file) {
        BusinessAssert.notNull(file, ErrorCodeAndMessage.create(9999981, "上传文件为空！"));
        return Result.success(systemFacade.uploadImage(file));
    }

    @ApiOperation(value = "添加子系统", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/add")
    public Result save(@RequestBody SysSystem system) {
        checkSystem(system);
        return Result.success(systemFacade.save(system));
    }

    private void checkSystem(SysSystem system) {

        BusinessAssert.notBlank(system.getSystemName(), ErrorCodeAndMessage.create(9999981, "子系统名称不能为空！"));
        BusinessAssert.notBlank(system.getSystemUrl(), ErrorCodeAndMessage.create(9999981, "子系统url不能为空！"));
        BusinessAssert.notBlank(system.getSystemPicture(), ErrorCodeAndMessage.create(9999981, "子系统图标不能为空！"));
        BusinessAssert.notBlank(system.getEnableFlag(), ErrorCodeAndMessage.create(9999981, "子系统状态不能为空！"));
        if (!RegexUtil.verifyUrl(system.getSystemUrl())) {
            BusinessAssert.throwIllegalArgumentException(ErrorCodeAndMessage.create(9999981, "输入的子系统url不合法！"));
        }
        BusinessAssert.maxlength(system.getSystemName(), 64, ErrorCodeAndMessage.create(9999981, "子系统名称字段长度过长"));
        BusinessAssert.maxlength(system.getSystemUrl(), 255, ErrorCodeAndMessage.create(9999981, "子系统url字段长度过长"));
        BusinessAssert.maxlength(system.getSystemPicture(), 255, ErrorCodeAndMessage.create(9999981, "子系统图标字段长度过长"));
    }

    @ApiOperation(value = "更新子系统", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@RequestBody SysSystem system) {
        checkSystem(system);
        return Result.success(systemFacade.updateById(system));
    }

    @ApiOperation(value = "删除子系统", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result removeById(@PathVariable Integer id) {
        BusinessAssert.notNull(id, ErrorCodeAndMessage.create(9999981, "子系统id不能为空"));
        return Result.success(systemFacade.removeById(id));
    }

}
