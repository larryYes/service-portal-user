package com.tyymt.wxplatform.service.portal.user.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyymt.wxplatform.service.portal.user.common.mapper.SysMenuMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu.MenuInfoRespDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {

    @Resource
    private SysMenuMapper sysMenuMapper;

    public int insertMenu(SysMenu sysMenu) {
        return sysMenuMapper.insert(sysMenu);
    }

    public int deleteByMenuId(Integer id) {
        return sysMenuMapper.deleteById(id);
    }

    public List<MenuInfoRespDto> selectMenu(Integer systemId,String systemName){
        return sysMenuMapper.selectMenu(systemId,systemName);
    }

}
