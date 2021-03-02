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

package com.tyymt.wxplatform.service.portal.user.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Data
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(value = "主键id")
	private Integer id;

	@ApiModelProperty(value = "用户名")
	@NotBlank
	private String userName;

	@ApiModelProperty(value = "用户姓名")
	@NotBlank
	private String nickName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "确认密码")
	@TableField(exist = false)
	private String confirmPassword;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "人员状态(0-启用 1-禁用)")
	private String enableFlag;

	@ApiModelProperty(value = "手机号")
	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
	@NotBlank
	private String phone;

	@ApiModelProperty(value = "邮箱")
	@Email
	private String email;

	@ApiModelProperty(value = "部门id")
	private Integer deptId;

	@ApiModelProperty(value = "备注")
	private String remark;

	@TableLogic
	private String delFlag;

}
