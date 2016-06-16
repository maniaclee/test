package maniac.lee.test.redis.springDataRedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by peng on 16/6/14.
 */

@SpringBootApplication
@ComponentScan("maniac.lee.test.redis.springDataRedis")
@RunWith(SpringJUnit4ClassRunner.class)
//@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
@ContextConfiguration(classes = {Config.class})
public class SpringDataRedisTest {

    @Autowired
    private SessionBiz sessionBiz;

    @Test
    public void test() {
        sessionBiz.setSessionCount(10L);
        assertEquals(Long.valueOf(10l), sessionBiz.getSessionCount());
    }
}
