package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yuchao on 15/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:config/csc/spring/appcontext-*.xml"

})
public class SpringBaseTest {
}
