package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.reader;

import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.BasePageRespDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Reader接口分页查询返参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口分页查询返参")
public class PageRespDto<T> extends BasePageRespDto implements Serializable {

    private static final long serialVersionUID = 1307133544251252010L;

    @ApiModelProperty(value = "Person视图类集合")
    private List<T> data;
}
