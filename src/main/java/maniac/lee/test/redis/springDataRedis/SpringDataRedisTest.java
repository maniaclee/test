package maniac.lee.test.redis.springDataRedis;

import maniac.lee.test.redis.springDataRedis.dto.User;
import maniac.lee.test.redis.springDataRedis.service.UserOperationsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by peng on 16/6/14.
 */

@ComponentScan("maniac.lee.test.redis.springDataRedis")
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
@ContextConfiguration(classes = {Config.class})
public class SpringDataRedisTest {

    @Autowired
    private SessionBiz sessionBiz;
    @Autowired
    private UserOperationsService userOperationsService;

    @Test
    public void test() {
        sessionBiz.setSessionCount(10L);
        System.out.println(sessionBiz.getSessionCount());
        assertEquals(Long.valueOf(10l), sessionBiz.getSessionCount());
    }

    @Test
    public void user() {
        userOperationsService.addUser(new User("id", "shit", "ssdddd"));
        System.out.println(userOperationsService.getUser("id"));
    }
}
