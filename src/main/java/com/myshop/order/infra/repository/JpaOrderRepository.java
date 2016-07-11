package com.myshop.order.infra.repository;

import com.myshop.common.jpa.JpaQueryUtils;
import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mac on 2016. 6. 18..
 */
@Repository
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(OrderNo id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public OrderNo nextOrderNo() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new OrderNo(number);
    }

    @Override
    public List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize) {
        TypedQuery<Order> query = entityManager.createQuery(
                "select o from Order o " +
                        "where o.orderer.memberId.id = :ordererId " +
                        JpaQueryUtils.toJPQLOrderBy("o", "number.number desc"),
                Order.class);
        query.setParameter("ordererId", ordererId);
        query.setFirstResult(startRow);
        query.setMaxResults(fetchSize);
        return query.getResultList();
    }
}
