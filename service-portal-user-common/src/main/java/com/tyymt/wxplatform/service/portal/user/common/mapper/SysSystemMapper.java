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

package com.tyymt.wxplatform.service.portal.user.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.system.SystemRoleRespDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Mapper
public interface SysSystemMapper extends BaseMapper<SysSystem> {

    /**
     * 根据用户id获取系统信息
     * @param userId
     * @return
     */
    List<SysSystem> findSystemByUserId(Integer userId);

    /**
     * 查询所有系统和系统所拥有的角色
     * @return
     */
    List<SystemRoleRespDto> getSystemAndRole(Integer userId);
}
