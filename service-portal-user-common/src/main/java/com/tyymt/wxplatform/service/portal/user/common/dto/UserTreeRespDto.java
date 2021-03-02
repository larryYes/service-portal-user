package com.tyymt.wxplatform.service.portal.user.common.dto;

import com.tyymt.wxplatform.service.portal.user.common.model.SysUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/28
 */
@Data
public class UserTreeRespDto extends SysUser {

    List<UserTreeRespDto> children = new ArrayList<>();
}
