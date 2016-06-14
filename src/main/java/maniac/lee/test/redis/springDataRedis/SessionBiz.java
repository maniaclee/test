package maniac.lee.test.redis.springDataRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionBiz {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public Long getSessionCount() {
        return Long.valueOf(redisTemplate.boundValueOps("pageViews").get());
    }

    public void setSessionCount(long count) {
        redisTemplate.boundValueOps("pageViews").set(String.valueOf(count));
    }

}
