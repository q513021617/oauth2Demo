package com.example.clientModel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = {"com.example.passwordmodel.dao"})
@EnableSwagger2
@SpringBootApplication
public class PasswordmodelApplication {



    public static void main(String[] args) {
        SpringApplication.run(PasswordmodelApplication.class, args);
    }

}
