/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tyymt.wxplatform.service.portal.user.api.controller;

import com.tyymt.wxplatform.service.portal.user.common.model.SysDept;
import com.tyymt.wxplatform.service.portal.user.facade.SysDeptFacade;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangfei
 * @create 2020-10-27 15:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
@Api(value = "dept", tags = "部门管理模块")
public class SysDeptController {

	private final SysDeptFacade sysDeptFacade;

	@ApiOperation(value = "分页获取部门信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
	@GetMapping("/page")
	public Result getDeptPage(PageReqDto req, SysDept vo) {
		BusinessAssert.notNull(req.getCurrent(), ErrorCodeAndMessage.create(9999981, "当前页面不能为空"));
		BusinessAssert.notNull(req.getSize(), ErrorCodeAndMessage.create(9999981, "页面大小不能为空"));
		return Result.success(sysDeptFacade.getDeptByPage(req, vo));
	}

	@ApiOperation(value = "获取部门信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
	@GetMapping("/list")
	public Result getDeptList(SysDept vo) {
		return Result.success(sysDeptFacade.getDeptList(vo));
	}

	@ApiOperation(value = "添加部门", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
	@PostMapping("/add")
	public Result save(@RequestBody SysDept sysDept) {
		BusinessAssert.notBlank(sysDept.getDeptName(), ErrorCodeAndMessage.create(9999981,"部门名称不能为空"));
		BusinessAssert.maxlength(sysDept.getDeptName(), 64, ErrorCodeAndMessage.create(9999981, "部门名称字段长度过长"));
		return Result.success(sysDeptFacade.save(sysDept));
	}

	@ApiOperation(value = "更新部门", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
	@PostMapping("/update")
	public Result update(@RequestBody SysDept sysDept) {
		BusinessAssert.notBlank(sysDept.getDeptName(), ErrorCodeAndMessage.create(9999981,"部门名称不能为空"));
		BusinessAssert.maxlength(sysDept.getDeptName(), 64, ErrorCodeAndMessage.create(9999981, "部门名称字段长度过长"));
		return Result.success(sysDeptFacade.updateById(sysDept));
	}

	@ApiOperation(value = "删除部门", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
	@DeleteMapping("/{id}")
	public Result removeById(@PathVariable Integer id) {
		BusinessAssert.notNull(id,ErrorCodeAndMessage.create(9999981,"部门id不能为空"));
		return Result.success(sysDeptFacade.removeById(id));
	}
}
