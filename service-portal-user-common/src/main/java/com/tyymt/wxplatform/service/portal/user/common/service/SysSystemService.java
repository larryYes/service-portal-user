package com.tyymt.wxplatform.service.portal.user.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyymt.wxplatform.service.portal.user.common.mapper.SysSystemMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.system.SystemRoleRespDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-14 16:11
 */
@Service
public class SysSystemService extends ServiceImpl<SysSystemMapper, SysSystem> {
    @Resource
    private SysSystemMapper mapper;

    public List<SysSystem> findSystemByUserId(Integer userId) {
        return mapper.findSystemByUserId(userId);
    }

    public List<SystemRoleRespDto> getSystemAndRole(Integer userId) {
        return mapper.getSystemAndRole(userId);
    }
}
