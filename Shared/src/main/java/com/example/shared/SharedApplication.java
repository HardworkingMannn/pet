package com.example.shared;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.example.shared.mapper")
@EnableDiscoveryClient
public class SharedApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharedApplication.class,args);
    }
}
