package com.gv.user_service.service;

import com.gv.user_service.dto.response.APIResponse;

public interface UserService {
    APIResponse getUserById(String name);
    APIResponse checkUserName(String name);
}
