package com.tyymt.wxplatform.service.portal.user.sdk.http.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询入参父类
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
public class BasePageReqDto {
    @ApiModelProperty(value = "当前页码")
    private int current = 1;

    @ApiModelProperty(value = "当前页码数据大小")
    private int size = 20;
}
