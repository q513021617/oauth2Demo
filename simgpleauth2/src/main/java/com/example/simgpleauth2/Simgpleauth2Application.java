package com.example.simgpleauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = {"com.example.simgpleauth2.dao"})
@EnableSwagger2
@SpringBootApplication
public class Simgpleauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Simgpleauth2Application.class, args);
    }

}
