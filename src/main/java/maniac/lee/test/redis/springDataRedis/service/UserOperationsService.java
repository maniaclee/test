package maniac.lee.test.redis.springDataRedis.service;

import maniac.lee.test.redis.springDataRedis.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class UserOperationsService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void addUser(User user) {
        // TODO Auto-generated method stub
        /*
         * boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
         * public Boolean doInRedis(RedisConnection redisConnection) throws
         * DataAccessException { RedisSerializer<String> redisSerializer =
         * redisTemplate .getStringSerializer(); byte[] key =
         * redisSerializer.serialize(user.getId()); byte[] value =
         * redisSerializer.serialize(user.getName()); return
         * redisConnection.setNX(key, value); } }); return result;
         */
        ValueOperations<String, User> valueops = redisTemplate.opsForValue();
        valueops.set(user.getId(), user);
    }


    public User getUser(String key) {
        ValueOperations<String, User> valueops = redisTemplate.opsForValue();
        User user = valueops.get(key);
        return user;
    }


}