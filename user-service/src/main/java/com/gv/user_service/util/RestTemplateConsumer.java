package com.gv.user_service.util;

import com.gv.user_service.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;

@Slf4j
@Component
public class RestTemplateConsumer {

    public User getForEntity(String name) {
        log.info("[getForEntity]");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(Constants.BASE_API_GITHUB_URL + "/" + name, User.class);

        //thread creation
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info("run +++++");
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return responseEntity.getBody();
    }

    public User getForObject(String name) {
        log.info("[getForObject]");
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(Constants.BASE_API_GITHUB_URL + "/" + name, User.class);
        return user;
    }


}
