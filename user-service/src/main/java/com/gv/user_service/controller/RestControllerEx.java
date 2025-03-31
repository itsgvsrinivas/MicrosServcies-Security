package com.gv.user_service.controller;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.model.User;
import com.gv.user_service.util.ResponseUtils;
import com.gv.user_service.util.RestTemplateConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/restEx")
public class RestControllerEx {

    private final RestTemplateConsumer restTemplateConsumer;

    @GetMapping("/getForEntity/{userName}")
    public ResponseEntity<APIResponse> getForEntity(@PathVariable(name = "userName") String userName) {
        log.info("[getForEntity] userName:" + userName);

        User user = restTemplateConsumer.getForEntity(userName);
        User user1 = restTemplateConsumer.getForObject(userName);
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, "0000", "Successful", user1, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getForObject/{userName}")
    public ResponseEntity<APIResponse> getForObject(@PathVariable(name = "userName") String userName) {
        log.info("[getForEntity]");

        User user = restTemplateConsumer.getForObject(userName);
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, "0000", "Successful", user, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/webClient1/{userName}")
    public ResponseEntity<APIResponse> getUserObj(@PathVariable(name = "userName") String userName) {
        log.info("[getUserObj]");

        //Mono<User> userMono = webClientConsumer.getUser1(userName);

        User user = restTemplateConsumer.getForObject(userName);
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, "0000", "Successful", user, null);
        return ResponseEntity.ok(apiResponse);
    }


}
