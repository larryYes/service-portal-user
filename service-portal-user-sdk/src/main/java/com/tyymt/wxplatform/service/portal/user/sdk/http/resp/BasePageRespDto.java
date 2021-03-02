package com.tyymt.wxplatform.service.portal.user.sdk.http.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询返参父类
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
public class BasePageRespDto {
    @ApiModelProperty(value = "当前页码")
    private Long current;

    @ApiModelProperty(value = "页数")
    private Long pages;

    @ApiModelProperty(value = "当前页码数据大小")
    private Long size;

    @ApiModelProperty(value = "总数")
    private Long total;
}
