package com.dianping.csc.common.service.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by yuchao on 15/4/16.
 */
@RunWith(CustomizeJunit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/config/spring/local/appcontext-*.xml"
})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class
        })
public class SpringBaseWithCustomizeRunnerTest {
}
