package com.tyymt.wxplatform.service.portal.user.sdk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Person视图类型
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Person视图类型")
public class PersonVo implements Serializable {

    private static final long serialVersionUID = 6572023684943923628L;
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "年龄")
    private int age;
}
