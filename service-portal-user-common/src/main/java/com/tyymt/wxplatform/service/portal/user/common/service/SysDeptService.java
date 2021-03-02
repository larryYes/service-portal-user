package com.tyymt.wxplatform.service.portal.user.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyymt.wxplatform.service.portal.user.common.mapper.SysDeptMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysDept;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-27 16:11
 */
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {
    @Resource
    private SysDeptMapper mapper;

    public IPage<List<SysDept>> getDeptByPage(Page page, SysDept vo) {
        return mapper.getDeptByPage(page, vo);
    }
}
