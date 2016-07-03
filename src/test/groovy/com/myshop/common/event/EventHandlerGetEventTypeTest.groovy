package com.myshop.common.event

import com.myshop.member.domain.PasswordChangedEvent
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 4..
 */
class EventHandlerGetEventTypeTest extends Specification {
    def PasswordChangedEvent evt = new PasswordChangedEvent("id", "newPw")

    def "익명클래스로 이벤트핸들러를 만들어 해당 이벤트를 처리할 수 있는지 확인한다."() {
        when:
        def handler = new EventHandler<PasswordChangedEvent>() {

            @Override
            void handle(PasswordChangedEvent event) {
            }
        }

        then:
        handler.canHandle(evt) == true
    }

    def "외부클래스로 이벤트핸들러를 만들어 해당 이벤트를 처리할 수 있는지 확인한다."() {
        when:
        def handler = new PasswordChangedHandler()

        then:
        handler.canHandle(evt) == true

    }

    def "내부클래스로 이벤트핸들러를 만들어 해당 이벤트를 처리할 수 있는지 확인한다."() {
        when:
        def handler = new InnerClassHandler()

        then:
        handler.canHandle(evt) == true
    }

    class InnerClassHandler implements EventHandler<PasswordChangedEvent> {

        @Override
        public void handle(PasswordChangedEvent event) {
        }
    }
}