package com.tyymt.wxplatform.service.portal.user.sdk.http.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huangfei
 * @create 2020-10-22 10:50
 */
@Data
@ApiModel(description = "user接口插入数据")
public class UserReqDto {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String remark;
}
