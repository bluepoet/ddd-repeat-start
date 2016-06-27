package com.myshop.order.command.domain;

/**
 * Created by Mac on 2016. 6. 24..
 */
public interface CancelPolicy {
    boolean hasCancellationPermission(Order order, Canceller canceller);
}
