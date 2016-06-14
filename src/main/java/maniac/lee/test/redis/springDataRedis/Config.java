package maniac.lee.test.redis.springDataRedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by peng on 16/6/14.
 */
@Configuration
public class Config {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

    }

    @Bean
    public RedisTemplate redisTemplate() {
    }

}
