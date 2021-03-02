package com.tyymt.wxplatform.service.portal.user.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.menu.MenuInfoRespDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/15
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 条件查询菜单
     * @param systemName systemId
     * @return
     */
    List<MenuInfoRespDto> selectMenu(Integer systemId,String systemName);
}
