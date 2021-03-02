package com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Reader接口查询单个记录入参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口查询单个记录入参")
public class ReadInfoReqDto implements Serializable {

    private static final long serialVersionUID = -3026091884322444597L;
    @ApiModelProperty(value = "主键id")
    private long id;

}
