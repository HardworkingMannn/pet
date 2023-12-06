package com.example.config;

import com.example.Model.constant.JedisConst;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Configuration
public class Config {
    @Bean
    public JwtUtil jwtUtil(){
        System.out.println("jwtUtil被创建");
        return new JwtUtil();
    }
    @Bean
    public Jedis jedis(){
        return new Jedis(JedisConst.JEDIS_HOST,JedisConst.JEDIS_PORT);
    }
}
