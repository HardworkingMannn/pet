package com.example.pet;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.example.pet.mapper")
@EnableDiscoveryClient
public class PetApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetApplication.class,args);
    }
}
