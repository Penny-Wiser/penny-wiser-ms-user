package com.penniless.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@Slf4j
public class PennyWiserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PennyWiserApplication.class, args);
        log.info("Started User Service Microservice...");
    }
}
