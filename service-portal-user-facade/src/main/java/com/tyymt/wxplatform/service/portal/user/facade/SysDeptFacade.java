package com.tyymt.wxplatform.service.portal.user.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyymt.wxplatform.service.portal.user.common.model.SysDept;
import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import com.tyymt.wxplatform.service.portal.user.common.service.SysDeptService;
import com.tyymt.wxplatform.service.portal.user.common.service.SysUserService;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import lombok.RequiredArgsConstructor;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.BusinessException;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangfei
 * @create 2020-10-27 15:34
 */
@Service
@RequiredArgsConstructor
public class SysDeptFacade {

    private final SysDeptService sysDeptService;

    private final SysUserService sysUserService;

    public Boolean save(SysDept sysDept) {
        SysDept entity = sysDeptService.getOne(Wrappers.<SysDept>query().lambda().eq(SysDept::getDeptName, sysDept.getDeptName()));
        BusinessAssert.isNull(entity, ErrorCodeAndMessage.create(9999981, "该部门已存在"));
        return sysDeptService.save(sysDept);
    }

    public Boolean updateById(SysDept sysDept) {
        SysDept dept = sysDeptService.getById(sysDept.getId());
        BusinessAssert.notNull(dept, ErrorCodeAndMessage.create(9999981, "记录已删除，请刷新！"));
        if (sysDept.getParentId() != null && sysDept.getId() != null) {
            if (sysDept.getId().equals(sysDept.getParentId())) {
                throw new BusinessException(ErrorCodeAndMessage.create(9999981, "所属部门不能修改为自己"));
            } else {
                List<SysDept> list = sysDeptService.list(Wrappers.<SysDept>query().lambda().eq(SysDept::getParentId, sysDept.getId()));
                list.forEach(a -> {
                    if (a.getId().equals(sysDept.getParentId())) {
                        throw new BusinessException(ErrorCodeAndMessage.create(9999981, "所属部门不能修改为自己的子部门"));
                    }
                });
            }
        }
        return sysDeptService.updateById(sysDept);
    }

    public Boolean removeById(Integer id) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        List<SysDept> list = sysDeptService.list(queryWrapper.lambda().eq(SysDept::getParentId, id));
        if (list.size() > 0) {
            throw new BusinessException(ErrorCodeAndMessage.create(9999981, "该部门下有子部门，不允许删除"));
        }
        QueryWrapper<SysUser> queryWrapperUser = new QueryWrapper<>();
        List<SysUser> userList = sysUserService.list(queryWrapperUser.lambda().eq(SysUser::getDeptId, id));
        if (userList.size() > 0) {
            throw new BusinessException(ErrorCodeAndMessage.create(9999981, "该部门下有用户，不可删除！"));
        }
        return sysDeptService.removeById(id);
    }

    public IPage getDeptByPage(PageReqDto req, SysDept vo) {
        Page page = new Page<>(req.getCurrent(), req.getSize());
        return sysDeptService.getDeptByPage(page, vo);
    }

    public List<SysDept> getDeptList(SysDept vo) {
        return sysDeptService.list(Wrappers.query(vo));
    }
}
