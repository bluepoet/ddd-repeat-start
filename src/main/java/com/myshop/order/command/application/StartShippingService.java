package com.myshop.order.command.application;

import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.myshop.order.command.application.CheckOrder.checkNoOrder;

/**
 * Created by Mac on 2016. 6. 26..
 */
@Service
public class StartShippingService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void startShipping(StartShippingRequest req) {
        Order order = orderRepository.findById(new OrderNo(req.getOrderNumber()));
        checkNoOrder(order);
        if(!order.matchVersion(req.getVersion())) {
            throw new OptimisticLockingFailureException("version conflict");
        }
        order.startShipping();
    }
}
