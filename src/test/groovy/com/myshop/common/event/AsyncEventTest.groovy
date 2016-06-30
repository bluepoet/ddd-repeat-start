package com.myshop.common.event

import spock.lang.Specification

import java.util.concurrent.Executors


/**
 * Created by Mac on 2016. 7. 1..
 */
class AsyncEventTest extends Specification {
    def "비동기처리 핸들러를 등록한 뒤, 이벤트를 발생시키고 처리에 예외가 없는지 확인한다."() {
        given:
        Events.init(Executors.newFixedThreadPool(3))
        Events.handleAsync({ evt -> throw new RuntimeException("") })

        when:
        Events.raise(new TestEvent())

        then:
        notThrown Exception
    }
}