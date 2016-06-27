package com.myshop.order.infra.paygate;

import com.myshop.order.command.application.RefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mac on 2016. 6. 24..
 */
@Component
public class ExternalRefundService implements RefundService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalRefundService.class);

    @Override
    public void refund(String orderNumber) {
        System.out.printf("refund order[%s]\n", orderNumber);
        logger.info("refund order[{}]\n", orderNumber);
    }
}
