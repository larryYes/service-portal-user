package com.tyymt.wxplatform.service.portal.user.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author huangfei
 * @create 2020-10-19 10:06
 */
@Data
@TableName("t_order")
public class Order {
    private Long id;

    private String name;

    private String type;

    private Date gmtCreate;

}
