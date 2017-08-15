package hys.common.utils;

import com.hys.common.utils.IdWorker;
import com.hys.common.utils.Loggers;
import com.hys.common.utils.Passwords;

import org.slf4j.Logger;
import org.testng.annotations.Test;

public class XPasswordTest {
    Logger logger = Loggers.getLogger(XPasswordTest.class);

    @Test
    public void t1() {
        logger.debug("{}", IdWorker.nextId());
        logger.debug(Passwords.encrypt("1"));
        logger.debug("{}", Passwords.match("123",
                "f85fb190d55652aba5b3e70a1889f8236d224bd1fa87c64944337488f62ba67fc465598c61033f0e"));
    }
}
