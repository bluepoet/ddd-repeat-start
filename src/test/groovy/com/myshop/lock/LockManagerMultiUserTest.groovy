package com.myshop.lock

import com.myshop.SpringIntTestConfig
import com.myshop.order.command.domain.OrderNo
import com.myshop.order.command.domain.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate
import spock.lang.Specification

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by bluepoet on 16. 7. 28..
 */
@SpringIntTestConfig
class LockManagerMultiUserTest extends Specification {
    @Autowired
    LockManager lockManager

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    PlatformTransactionManager txMgr

    @Autowired
    OrderRepository orderRepository

    TransactionTemplate tt

    void setup() {
        jdbcTemplate.update("truncate table locks")
        tt = new TransactionTemplate(txMgr)
    }

    def "동시사용자가 여러명일 때 락을 테스트한다."() {
        given:
        tt = new TransactionTemplate(txMgr)
        def lockFailCount = new AtomicInteger(0)
        def lockSuccessCount = new AtomicInteger(0)

        def usecase = createUserCase(lockFailCount, lockSuccessCount)
        def numberOfUsers = 40
        def executorService = Executors.newFixedThreadPool(numberOfUsers)

        when:
        (1..numberOfUsers).each {
            executorService.submit(usecase)
        }
        executorService.shutdown()
        executorService.awaitTermination(3000, TimeUnit.SECONDS)

        then:
        lockFailCount.get() == numberOfUsers - 1
        lockSuccessCount.get() == 1
    }

    def createUserCase(AtomicInteger lockFailCount, AtomicInteger lockSuccessCount) {
        Runnable usecase = { ->
            LockId lockId = null
            try {
                lockId = tt.execute(new TransactionCallback<LockId>() {
                    @Override
                    LockId doInTransaction(TransactionStatus transactionStatus) {
                        return runFuncWithTryLock()
                    }
                })
            } catch (LockException e) {
                lockFailCount.incrementAndGet()
                return
            }
            lockSuccessCount.incrementAndGet()

            sleep(2000)

            final LockId finalLockId = lockId
            tt.execute({ status -> runFuncWithReleaseLock(finalLockId) })
        }
    }

    def runFuncWithTryLock() {
        def lockId = lockManager.tryLock("temptype", "001")
        orderRepository.findById(new OrderNo("ORDER-001"))
        sleep(100)
        return lockId
    }

    def runFuncWithReleaseLock(lockId) {
        lockManager.releaseLock(lockId)
        return null
    }
}