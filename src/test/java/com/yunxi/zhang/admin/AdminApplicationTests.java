package com.yunxi.zhang.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.sql.DataSource;

@SpringBootTest
class AdminApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate template;
    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }
    @Test
    void redisTest(){
//        redisTemplate.opsForValue().append("name","张三");
//        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    @Test
    void passwordEncoder(){
        String password = BCrypt.hashpw("123",BCrypt.gensalt());
        System.out.println(password);
        boolean cheack = BCrypt.checkpw("123","$2a$10$M4bPqKwYB9HzL4g3qQzzu.OwwXWHiF4GkLylCUPoQRviB9B3czUIq");
        System.out.println(cheack);
    }
}
