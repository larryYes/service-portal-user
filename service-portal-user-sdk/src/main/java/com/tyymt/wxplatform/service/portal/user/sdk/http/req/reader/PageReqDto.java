package com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader;


import com.tyymt.wxplatform.service.portal.user.sdk.http.req.BasePageReqDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Reader接口分页查询入参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口分页查询入参")
public class PageReqDto extends BasePageReqDto implements Serializable {
    private static final long serialVersionUID = -6596337430471790137L;
}
