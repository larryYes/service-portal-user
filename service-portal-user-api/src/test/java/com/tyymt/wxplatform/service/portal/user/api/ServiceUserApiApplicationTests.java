package com.tyymt.wxplatform.service.portal.user.api;

import com.tyymt.wxplatform.service.portal.user.common.model.SysMenu;
import com.tyymt.wxplatform.service.portal.user.common.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServiceUserApiApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;
    @Test
    void contextLoads() {
    }
    @Test
    void selectAll(){
        List<SysMenu> list = sysMenuService.list();
        System.out.println(list);
    }
}
