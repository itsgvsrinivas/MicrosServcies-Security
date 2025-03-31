package com.gv.user_service.controller;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.model.Greet;
import com.gv.user_service.service.UserService;
import com.gv.user_service.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    public ResponseEntity<APIResponse> getGreeting() {
        log.info("[getGreeting]");
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, "0000", "Successful", Greet.builder().msg("Good Morning").build(), null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<APIResponse> getUser(@PathVariable(name = "userName") String userName) {
        log.info("[getUser]");
        APIResponse apiResponse = userService.getUserById(userName);
        return ResponseEntity.ok(apiResponse);
    }

}
