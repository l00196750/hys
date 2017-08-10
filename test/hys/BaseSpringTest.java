package hys;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent")
@ContextConfiguration({
    "classpath:applicationContext.xml", "classpath:spring-security.xml", "classpath:spring-cache.xml"
})
public abstract class BaseSpringTest {

}
