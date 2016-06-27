package com.myshop.order.ui;

import com.myshop.order.command.application.CancelOrderService;
import com.myshop.order.command.domain.Canceller;
import com.myshop.order.command.domain.OrderNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mac on 2016. 6. 24..
 */
@Controller
public class CancelOrderController {
    @Autowired
    private CancelOrderService cancelOrderService;

    @RequestMapping("/my/orders/{orderNo}/cancel")
    public String orderDetail(@PathVariable("orderNo") String orderNo, ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cancelOrderService.cancel(new OrderNo(orderNo), new Canceller(user.getUsername()));
        return "my/orderCanceled";
    }
}
