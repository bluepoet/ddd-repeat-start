package com.myshop.eventstore.infra

import com.fasterxml.jackson.databind.ObjectMapper
import com.myshop.SpringIntTestConfig
import com.myshop.eventstore.api.EventEntry
import com.myshop.eventstore.api.EventStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 29..
 */
@SpringIntTestConfig
class JdbcEventStoreTest extends Specification {
    @Autowired
    EventStore eventStore

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    ObjectMapper objectMapper

    def "EventStore에 이벤트를 저장한다."() {
        when:
        eventStore.save(new SampleEvent("name", 10))

        then:
        def payload = jdbcTemplate.queryForObject(
                "select payload from evententry order by id desc limit 0, 1", String.class);

        def savedPayload = objectMapper.readValue(payload, SampleEvent.class)
        savedPayload.getName() == "name"
        savedPayload.getValue() == 10
    }

    def "EventStore에서 이벤트를 가져온다."() {
        given:
        List<EventEntry> entries = eventStore.get(1, 2)

        expect:
        SampleEvent event = objectMapper.readValue(entries.get(index).getPayload(), SampleEvent.class)
        event.getName() == name
        event.getValue() == value

        where:
        index | name    | value
        0     | "name2" | 12
        1     | "name3" | 13
    }
}