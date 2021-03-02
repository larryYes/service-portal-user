package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.reader;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Reader接口查询单个记录返参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口查询单个记录返参")
public class ReadInfoRespDto implements Serializable {

    private static final long serialVersionUID = 8351144227042740191L;
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "年龄")
    private int age;
}
