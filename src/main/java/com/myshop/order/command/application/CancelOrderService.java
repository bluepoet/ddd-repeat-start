package com.myshop.order.command.application;

import com.myshop.common.event.Events;
import com.myshop.order.command.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mac on 2016. 6. 24..
 */
@Service
public class CancelOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RefundService refundService;

    @Autowired
    private CancelPolicy cancelPolicy;

    @Transactional
    public void cancel(OrderNo orderNo, Canceller canceller) {
        Events.handle((OrderCanceledEvent evt) -> refundService.refund(orderNo.getNumber()));

        Order order = findOrder(orderNo);
        if (!cancelPolicy.hasCancellationPermission(order, canceller)) {
            throw new NoCancellablePermissionException();
        }
        order.cancel();

//        Events.reset();  // 반복되는 코드는 AOP로 처리할 수 있음
    }

    private Order findOrder(OrderNo orderNo) {
        Order order = orderRepository.findById(orderNo);
        if (order == null) throw new NoOrderException();
        return order;
    }
}
