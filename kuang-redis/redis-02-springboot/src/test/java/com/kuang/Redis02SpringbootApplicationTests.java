package com.kuang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushAll();
//        connection.flushDb();

        redisTemplate.opsForValue().set("mykey", "kuangshen");
        System.out.println(redisTemplate.opsForValue().get("mykey"));

    }

}
