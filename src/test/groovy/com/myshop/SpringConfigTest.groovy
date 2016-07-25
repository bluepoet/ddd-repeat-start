package com.myshop

import com.myshop.common.event.EventStoreHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 26..
 */
@SpringApplicationConfiguration(ShopApplication.class)
class SpringConfigTest extends Specification {
    @Autowired
    ApplicationContext applicationContext

    def "eventStoreHandler는 EventAOP에 의해 적용되지 않는다."() {
        when:
        def bean = applicationContext.getBean("eventStoreHandler")

        then:
        bean != null
        bean instanceof EventStoreHandler
    }
}