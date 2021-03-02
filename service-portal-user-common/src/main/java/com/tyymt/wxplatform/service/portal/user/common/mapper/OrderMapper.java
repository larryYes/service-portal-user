package com.tyymt.wxplatform.service.portal.user.common.mapper;

/**
 * @author huangfei
 * @create 2020-10-19 10:07
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyymt.wxplatform.service.portal.user.common.model.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Dao层
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
