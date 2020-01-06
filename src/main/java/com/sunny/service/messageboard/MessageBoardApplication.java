package com.sunny.service.messageboard;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.sunny.service.messageboard")
public class MessageBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageBoardApplication.class,args);
    }
}