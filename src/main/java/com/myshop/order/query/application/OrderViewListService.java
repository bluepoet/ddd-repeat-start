package com.myshop.order.query.application;

import com.myshop.common.jpaspec.Specification;
import com.myshop.common.model.Page;
import com.myshop.order.query.dao.OrderSummaryDao;
import com.myshop.order.query.dto.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 26..
 */
@Component
public class OrderViewListService {
    @Autowired
    private OrderSummaryDao orderSummaryDao;

    @Transactional
    public Page<OrderSummary> getList(ListRequest listReq) {
        Specification<OrderSummary> spec = null;
        int firstRow = (listReq.getPage() - 1) * listReq.getSize();
        List<OrderSummary> select = orderSummaryDao.select(spec, firstRow, listReq.getSize());
        long count = orderSummaryDao.counts(spec);
        return new Page<>(select, listReq.getPage(), listReq.getSize(), count);
    }
}
