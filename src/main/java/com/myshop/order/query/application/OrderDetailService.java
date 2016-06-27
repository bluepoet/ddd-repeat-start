package com.myshop.order.query.application;

import com.myshop.catalog.application.ProductService;
import com.myshop.catalog.domain.product.Product;
import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Mac on 2016. 6. 23..
 */
@Component
public class OrderDetailService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Optional<OrderDetail> getOrderDetail(String orderNumber) {
        Order order = orderRepository.findById(new OrderNo(orderNumber));
        if (order == null) return Optional.empty();

        List<OrderLineDetail> orderLines = order.getOrderLines().stream()
                .map(orderLine -> {
                    Optional<Product> productOpt = productService.getProduct(orderLine.getProductId().getId());
                    return new OrderLineDetail(orderLine, productOpt.get());
                }).collect(Collectors.toList());
        return Optional.of(new OrderDetail(order, orderLines));
    }
}
