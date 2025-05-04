package com.gv.user_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootApplication
public class UserServiceApplication {

    @Value("${myapp.message}")
    private String message;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
        String[] beans = context.getBeanDefinitionNames();
        System.out.println("Total Beans auto configured :: " + beans.length);

        String staticEncoded = new BCryptPasswordEncoder().encode("gv123");
        System.out.println("staticEncoded :: " + staticEncoded);
        //for (String s : beans)
        //System.out.println("Bean: " + s);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            log.info("[UserServiceApplication] >> [applicationRunner]");
        };
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("[UserServiceApplication] >> [commandLineRunner]");
        };
    }
}
