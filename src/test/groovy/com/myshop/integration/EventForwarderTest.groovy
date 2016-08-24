package com.myshop.integration

import com.myshop.eventstore.api.EventEntry
import com.myshop.eventstore.api.EventStore
import com.myshop.integration.infra.MemoryOffsetStore
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 8. 24..
 */
class EventForwarderTest extends Specification {
    OffsetStore offsetStore = new MemoryOffsetStore()
    EventStore fakeEventStore = new EventStore() {
        def events = Arrays.asList(
                createEventEntry(1L), createEventEntry(2L), createEventEntry(3L), createEventEntry(4L), createEventEntry(5L), createEventEntry(6L));

        @Override
        void save(Object event) {
        }

        @Override
        List<EventEntry> get(long offset, long limit) {
            if (offset >= events.size()) return Collections.emptyList()
            int toIdx = (int) (events.size() < offset + limit ? events.size() : offset + limit)
            int fromIdx = (int) offset
            return events.subList(fromIdx, toIdx)
        }
    }

    List<EventEntry> sendedEvent = new ArrayList<>()
    EventSender fakeEventSender = { event -> sendedEvent.add(event) }

    EventForwarder forwarder

    void setup() {
        forwarder = new EventForwarder()
        forwarder.setLimitSize(4)
        forwarder.setOffsetStore(offsetStore)
        forwarder.setEventSender(fakeEventSender)
        forwarder.setEventStore(fakeEventStore)
    }

    def "쌓인 이벤트를 처리하고 결과를 검증한다."() {
        when:
        forwarder.getAndSend()

        then:
        sendedEvent.size() == 4

        when:
        sendedEvent.clear()
        forwarder.getAndSend()

        then:
        sendedEvent.size() == 2

        when:
        sendedEvent.clear()
        forwarder.getAndSend()

        then:
        sendedEvent.size() == 0
    }

    def createEventEntry(long id) {
        return new EventEntry(id, "java.lang.Integer", "application/json", Long.toString(id), System.currentTimeMillis())
    }
}