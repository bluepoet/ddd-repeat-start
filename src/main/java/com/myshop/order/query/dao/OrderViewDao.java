package com.myshop.order.query.dao;

import com.myshop.order.query.dto.OrderView;

import java.util.List;

/**
 * Created by Mac on 2016. 7. 8..
 */
public interface OrderViewDao {
    List<OrderView> selectByOrderer(String ordererId);
}
