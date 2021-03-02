package com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Reader接口根据名字查询单个记录入参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口根据名字查询单个记录入参")
public class ReadInfoByNameReqDto implements Serializable {

    private static final long serialVersionUID = -2815128456256487190L;
    @ApiModelProperty(value = "名字")
    private String name;
}
