package com.gv.user_service.controller;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.util.Constants;
import com.gv.user_service.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/postAuth")
public class PostAuthController {

    @GetMapping("/contact")
    public ResponseEntity<APIResponse> getContactNo() {
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, Constants.STATUS_CODE_SUCCESS,
                Constants.STATUS_DESC_SUCCESS, "+91 9878675689", null);
        return ResponseEntity.ok(apiResponse);
    }
}
