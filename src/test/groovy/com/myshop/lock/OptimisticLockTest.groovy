package com.myshop.lock

import com.myshop.SpringIntTestConfig
import com.myshop.common.model.Address
import com.myshop.order.command.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Created by bluepoet on 16. 7. 28..
 */
@SpringIntTestConfig
class OptimisticLockTest extends Specification {
    @Autowired
    OrderRepository orderRepository

    @Autowired
    TransactionTemplate txTemplate

    def "낙관적 락을 테스트한다."() {
        given:
        def executorService = Executors.newFixedThreadPool(3)

        when:
        Future<Result> changeResult = executorService.submit({ changeShippingInfo() } as Callable<Result>)
        Future<Result> startResult = executorService.submit({ startShipping() } as Callable<Result>)

        then:
        changeResult.get().isSuccess() == true
        startResult.get().isSuccess() == false
        startResult.get().getException() instanceof OptimisticLockingFailureException

        executorService.shutdown()
    }

    def changeShippingInfo() {
        try {
            txTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Order order = orderRepository.findById(new OrderNo("ORDER-001"))
                    sleep(1000)
                    order.changeShippingInfo(new ShippingInfo(new Address("zip", "addr1", "addr2"), "msg", new Receiver("name", "phone")))
                }
            })
            return Result.SUCCESS
        } catch (Exception e) {
            return Result.fail(e)
        }
    }

    def startShipping() {
        try {
            txTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Order order = orderRepository.findById(new OrderNo("ORDER-001"))
                    sleep(3000)
                    order.startShipping()
                }
            })
            return Result.SUCCESS
        } catch (Exception e) {
            return Result.fail(e)
        }
    }

    static class Result {
        static Result SUCCESS = new Result(true, null)

        static Result fail(Exception ex) {
            return new Result(false, ex)
        }

        boolean success
        Exception exception

        Result(boolean success, Exception exception) {
            this.success = success
            this.exception = exception
        }

        boolean isSuccess() {
            return success
        }

        Exception getException() {
            return exception
        }
    }
}

