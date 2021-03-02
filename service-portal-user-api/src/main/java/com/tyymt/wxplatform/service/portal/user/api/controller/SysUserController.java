package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.tyymt.wxplatform.service.portal.user.common.constant.CommonConstants;
import com.tyymt.wxplatform.service.portal.user.common.dto.SysUserDTO;
import com.tyymt.wxplatform.service.portal.user.common.vo.SysUserVO;
import com.tyymt.wxplatform.service.portal.user.common.vo.export.SysUserExportVO;
import com.tyymt.wxplatform.service.portal.user.facade.SysUserFacade;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.user.UserRoleAuthReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Api(value = "user", tags = "用户管理模块")
@RestController
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    SysUserFacade sysUserFacade;

    final BASE64Decoder decoder = new BASE64Decoder();

    @ApiOperation(value = "分页获取用户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/page")
    public Result getUserPage(PageReqDto req, SysUserVO vo) {
        BusinessAssert.notNull(req.getCurrent(), ErrorCodeAndMessage.create(9999981, "当前页面不能为空"));
        BusinessAssert.notNull(req.getSize(), ErrorCodeAndMessage.create(9999981, "页面大小不能为空"));
        return Result.success(sysUserFacade.getUserWithRolePage(req, vo));
    }

    @ApiOperation(value = "根据用户id获取用户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/info/{userId}")
    public Result getUserInfo(@PathVariable Integer userId) {
        BusinessAssert.notNull(userId, ErrorCodeAndMessage.create(9999981, "用户id不能为空"));
        return Result.success(sysUserFacade.getUserInfo(userId));
    }


    @ApiOperation(value = "添加用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/add")
    public Result add(@RequestBody SysUserDTO sysUser) {
        checkUser(sysUser);
        return Result.success(sysUserFacade.saveUser(sysUser));
    }

    @SneakyThrows
    private void checkUser(SysUserDTO sysUser) {
        BusinessAssert.notBlank(sysUser.getUserName(), ErrorCodeAndMessage.create(9999981, "用户名不能为空"));
        BusinessAssert.notBlank(sysUser.getNickName(), ErrorCodeAndMessage.create(9999981, "姓名不能为空"));
        BusinessAssert.notBlank(sysUser.getPassword(), ErrorCodeAndMessage.create(9999981, "密码不能为空"));
        BusinessAssert.notBlank(sysUser.getConfirmPassword(), ErrorCodeAndMessage.create(9999981, "确认密码不能为空"));
        sysUser.setPassword(new String(decoder.decodeBuffer(sysUser.getPassword()), "UTF-8"));
        sysUser.setConfirmPassword(new String(decoder.decodeBuffer(sysUser.getConfirmPassword()), "UTF-8"));

        if (!sysUser.getPassword().equals(sysUser.getConfirmPassword())) {
            BusinessAssert.throwIllegalArgumentException(ErrorCodeAndMessage.create(9999981, "密码不一致"));
        }
        BusinessAssert.notNull(sysUser.getSystemList(), ErrorCodeAndMessage.create(9999981, "用户权限不能为空"));
        BusinessAssert.notNull(sysUser.getDeptId(), ErrorCodeAndMessage.create(9999981, "所属部门不能为空"));
        BusinessAssert.notNull(sysUser.getEnableFlag(), ErrorCodeAndMessage.create(9999981, "人员状态不能为空"));

        checkCode(sysUser);

    }

    /**
     * 校验参数
     *
     * @param sysUser
     */
    private void checkCode(SysUserDTO sysUser) {
        BusinessAssert.pattern(sysUser.getPhone(), "^1(3|4|5|6|7|8|9)\\d{9}$", ErrorCodeAndMessage.create(9999981, "手机号码格式错误"));
        BusinessAssert.pattern(sysUser.getEmail(), "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$", ErrorCodeAndMessage.create(9999981, "邮箱格式不合法"));
        BusinessAssert.maxlength(sysUser.getUserName(), 64, ErrorCodeAndMessage.create(9999981, "用户名字段长度过长"));
        BusinessAssert.maxlength(sysUser.getPhone(), 11, ErrorCodeAndMessage.create(9999981, "手机号码长度过长"));
        BusinessAssert.maxlength(sysUser.getNickName(), 64, ErrorCodeAndMessage.create(9999981, "姓名字段长度过长"));
        BusinessAssert.maxlength(sysUser.getEmail(), 255, ErrorCodeAndMessage.create(9999981, "电子邮箱字段长度过长"));
        BusinessAssert.maxlength(sysUser.getRemark(), 255, ErrorCodeAndMessage.create(9999981, "备注字段长度过长"));
    }

    @ApiOperation(value = "修改用户角色", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@RequestBody SysUserDTO sysUser) {
        checkCode(sysUser);
        sysUser.setPassword(null);

        return Result.success(sysUserFacade.updateById(sysUser));
    }

    @ApiOperation(value = "修改用户密码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody SysUserDTO sysUser) {
        BusinessAssert.notNull(sysUser.getId(), ErrorCodeAndMessage.create(9999981, "用户名id为空"));
        BusinessAssert.notBlank(sysUser.getPassword(), ErrorCodeAndMessage.create(9999981, "密码不能为空"));
        BusinessAssert.notBlank(sysUser.getConfirmPassword(), ErrorCodeAndMessage.create(9999981, "确认密码不能为空"));
        if (!sysUser.getPassword().equalsIgnoreCase(sysUser.getConfirmPassword())) {
            BusinessAssert.throwIllegalArgumentException(ErrorCodeAndMessage.create(9999981, "密码不一致"));
        }
        return Result.success(sysUserFacade.updatePassword(sysUser));
    }

    // TODO：待修改
    @SneakyThrows
    @ApiOperation(value = "根据用户id获取系统权限", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("/system/{userId}")
    public Result getSystem(
            @ApiParam(name = "userId", value = "用户Id")
            @PathVariable Integer userId
            ) {
        BusinessAssert.notNull(userId, ErrorCodeAndMessage.create(9999981, "用户id不能为空"));
        return Result.success(sysUserFacade.findSystemByUserId(userId));
    }

    @ApiOperation(value = "导出用户列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/export")
    public void exportData(@RequestBody SysUserVO vo, HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = null;
        try {
            fileName = URLEncoder.encode("用户列表", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        List<SysUserDTO> userWithRole = sysUserFacade.getUserWithRole(vo);
        List<SysUserExportVO> datas = userWithRole.stream().map(a -> {
            SysUserExportVO userExportVO = new SysUserExportVO();
            BeanUtils.copyProperties(a, userExportVO);
            userExportVO.setEnableFlag(CommonConstants.STATUS_NORMAL.equals(userExportVO.getEnableFlag()) ? "启用" : "禁用");
            userExportVO.setSystemAndAdmin(a.getSystemList().stream().map(b -> b.getSystemName() + ";").collect(Collectors.joining()));
            return userExportVO;
        }).collect(Collectors.toList());

        EasyExcel.write(response.getOutputStream(), SysUserExportVO.class).sheet("用户列表").doWrite(datas);

    }

    @ApiOperation(value = "删除用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result removeById(@PathVariable Integer id) {
        BusinessAssert.notNull(id, ErrorCodeAndMessage.create(9999981, "用户id不能为空"));
        return Result.success(sysUserFacade.removeById(id));
    }

    @ApiOperation(value = "给用户分配角色权限", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/auth")
    public Result doAssign(
            @ApiParam(name = "UserRoleAuthReqDto", value = "用户分配角色权限入参", required = true)
            @RequestBody UserRoleAuthReqDto urAuthReqDto) {
        BusinessAssert.notNull(urAuthReqDto.getUserId(), ErrorCodeAndMessage.create(9999981, "用户id不能为空"));
        BusinessAssert.notNull(urAuthReqDto.getRoleId(), ErrorCodeAndMessage.create(9999982, "角色id不能为空"));
        return Result.success(sysUserFacade.saveUserRoleRelationShip(urAuthReqDto));
    }

    @ApiOperation(value = "查询用户对应系统的所有权限", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @GetMapping("currentuser")
    public Result getCurrentUserAuth(
            @ApiParam(name = "systemCode", value = "系统代码", required = false)
            @RequestParam(defaultValue = "SSOWEB") String systemCode,
            @ApiParam(name = "userInfo", value = "网关自动回传的用户信息", required = false, hidden=true)
            @RequestHeader(value = "userInfo",required = false) String userInfo,
            @RequestParam(required = false) Integer id
            ) throws IOException {
        Integer userId;
        if(StringUtils.isEmpty(userInfo)){
            userId=id;
        }else{
            String user = new String(decoder.decodeBuffer(userInfo), "UTF-8");
            userId = JSONObject.parseObject(user).getInteger("id");
        }
        return Result.success(sysUserFacade.getAuth(userId, systemCode));
    }
}
