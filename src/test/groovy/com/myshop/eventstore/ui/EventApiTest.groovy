package com.myshop.eventstore.ui

import com.myshop.ShopApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

/**
 * Created by bluepoet on 2016. 7. 29..
 */
@SpringApplicationConfiguration(ShopApplication.class)
@WebAppConfiguration
@Sql("classpath:shop-int-test.sql")
class EventApiTest extends Specification {
    @Autowired
    WebApplicationContext context

    MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    def "이벤트를 가져온다."() {
        expect:
        mockMvc.perform(get("/api/events").param("offset", "0").param("limit", "2"))
                .andDo(print())
                .andExpect(jsonPath("\$[" + index + "].id").value(id))
                .andExpect(jsonPath("\$[" + index + "].type").value(type))
                .andExpect(jsonPath("\$[" + index + "].payload").value(payload))

        where:
        index | id | type                                      | payload
        0     | 1  | "com.myshop.eventstore.infra.SampleEvent" | "{\"name\": \"name1\", \"value\": 11}"
        1     | 2  | "com.myshop.eventstore.infra.SampleEvent" | "{\"name\": \"name2\", \"value\": 12}"

    }
}