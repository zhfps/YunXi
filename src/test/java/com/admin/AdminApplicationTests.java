//package com.admin;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//
//import javax.sql.DataSource;
//import java.util.Date;
//
//@SpringBootTest
//class AdminApplicationTests {
//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    StringRedisTemplate redisTemplate;
//    @Qualifier("redisTemplate")
//    @Autowired
//    RedisTemplate template;
//    @Test
//    void contextLoads() {
//        System.out.println(dataSource.getClass());
//    }
//    @Test
//    void redisTest(){
////        redisTemplate.opsForValue().append("name","张三");
////        System.out.println(redisTemplate.opsForValue().get("name"));
//    }
//    @Test
//    void passwordEncoder(){
//        String password = BCrypt.hashpw("admin",BCrypt.gensalt());
//        System.out.println(password);
//        boolean cheack = BCrypt.checkpw("admin","$2a$10$WI0ISkzS322sV0EoHQWfA.39kmTtvAL6jVBKm4ari6h/LDuJiLEw.");
//        System.out.println(cheack);
//    }
//    @Test
//    void JwtTest(){
//        //生成token
//
//        JwtBuilder builder= Jwts.builder().setId("888") .setSubject("小白")
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256,"miaomiaojiang");
//
//        String token = builder.compact();
//
//        System.out.println( token );
//
//        //解析token
//        Claims claims =Jwts.parser().setSigningKey("miaomiaojiang").parseClaimsJws(token).getBody();
//        System.out.println("id:"+claims.getId());
//        System.out.println("subject:"+claims.getSubject());
//        System.out.println("IssuedAt:"+claims.getIssuedAt());
//    }
//}
