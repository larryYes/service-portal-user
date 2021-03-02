package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.system;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/2/22
 */
@Data
@ApiModel(description = "所有系统和角色列表")
public class SystemRoleRespDto {

    private String id;

    private Integer parentId;

    private String name;

    List<SystemRoleRespDto> children = new ArrayList<>();
}
