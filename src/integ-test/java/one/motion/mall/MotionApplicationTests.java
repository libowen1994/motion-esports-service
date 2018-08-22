package one.motion.mall;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest
@TestPropertySource(properties = {"management.server.port=0"})
public class MotionApplicationTests extends AbstractTestNGSpringContextTests {


    @Test
    public void contextLoads() {
    }

}
