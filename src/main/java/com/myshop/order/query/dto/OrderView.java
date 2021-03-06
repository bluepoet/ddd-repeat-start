package com.myshop.order.query.dto;

import com.myshop.catalog.domain.product.Product;
import com.myshop.member.domain.Member;
import com.myshop.order.command.domain.Order;

/**
 * Created by Mac on 2016. 7. 8..
 */
public class OrderView {
    private Order order;
    private Member member;
    private Product product;

    public OrderView(Order order, Member member, Product product) {
        this.order = order;
        this.member = member;
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}
