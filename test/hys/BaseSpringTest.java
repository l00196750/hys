package hys;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent")
@ContextConfiguration({
    "classpath:applicationContext.xml", "classpath:spring-security.xml", "classpath:spring-cache.xml"
})
public abstract class BaseSpringTest extends AbstractTestNGSpringContextTests {
}
