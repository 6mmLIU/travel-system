package com.example.travelsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.travelsystem.mapper") // 扫描 Mapper 接口
public class TravelSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSystemApplication.class, args);
    }
}
