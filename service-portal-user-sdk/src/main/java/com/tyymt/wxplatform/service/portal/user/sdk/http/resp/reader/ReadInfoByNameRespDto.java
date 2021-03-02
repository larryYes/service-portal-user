package com.tyymt.wxplatform.service.portal.user.sdk.http.resp.reader;

import com.tyymt.wxplatform.service.portal.user.sdk.vo.PersonVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Reader接口根据名字查询单个记录返参
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-09
 */
@Data
@ApiModel(description = "Reader接口根据名字查询单个记录返参")
public class ReadInfoByNameRespDto implements Serializable {
    private static final long serialVersionUID = -4301679062078657155L;
    @ApiModelProperty(value = "Person视图类集合")
    private List<PersonVo> data;
}
