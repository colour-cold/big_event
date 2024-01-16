package me.colourcold;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void set() {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        op.set("username","1");
    }

    @Test
    public void setTime() {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        op.set("id","1",15, TimeUnit.SECONDS);
    }
}
