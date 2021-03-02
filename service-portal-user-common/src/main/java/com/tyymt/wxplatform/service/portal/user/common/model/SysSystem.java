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
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author huangfei
 * @create 2020-10-15 15:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSystem extends Model<SysSystem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "子系统代码")
    private String systemCode;

    @ApiModelProperty(value = "子系统名称")
    private String systemName;

    @ApiModelProperty(value = "子系统图标")
    private String systemPicture;

    @ApiModelProperty(value = "访问url")
    private String systemUrl;

    @ApiModelProperty(value = "子系统负责人")
    private String leaderName;

    @ApiModelProperty(value = "负责人手机号")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "子系统状态(0-启用 1-禁用)")
    private String enableFlag;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 是否为admin
     */
    //@ApiModelProperty(value = "是否为admin(0-是，1-否）")
    //@TableField(exist = false)
    //private String isAdmin;

}
