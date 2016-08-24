package com.myshop.common.event

import com.myshop.member.domain.PasswordChangedEvent
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 8. 24..
 */
class EventStoreHandlerTest extends Specification {
    def "이벤트를 처리할 수 있는지 검증한다."() {
        given:
        def storeHandler = new EventStoreHandler()

        expect:
        storeHandler.canHandle(object) == result

        where:
        object                                  | result
        new Object()                            | true
        "string"                                | true
        new PasswordChangedEvent("id", "newPw") | true
    }
}