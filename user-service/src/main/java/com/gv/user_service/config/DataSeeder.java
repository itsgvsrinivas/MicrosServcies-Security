package com.gv.user_service.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataSeeder {

    @PostConstruct
    public void init() {
        log.info("[DataSeeder] >> [init]");
    }
}
