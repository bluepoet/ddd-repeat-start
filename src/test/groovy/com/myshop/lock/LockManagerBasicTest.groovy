package com.myshop.lock

import com.myshop.SpringIntTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 26..
 */
@SpringIntTestConfig
class LockManagerBasicTest extends Specification {
    @Autowired
    LockManager lockManager

    @Autowired
    JdbcTemplate jdbcTemplate

    void setup() {
        jdbcTemplate.update("truncate table locks")
    }

    def "잠금을 시도한다."() {
        given:
        def lock = lockManager.tryLock("type1", "id1")

        when:
        lockManager.tryLock("type1", "id1")

        then:
        thrown AlreadyLockedException

        when:
        lockManager.releaseLock(lock)

        then:
        def count = jdbcTemplate.queryForObject(
                "select count(*) from locks where type = ? and id = ?",
                Integer.class,
                "type1", "id1").intValue()
        count == 0
    }
}