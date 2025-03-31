package com.gv.user_service.service.impl;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public APIResponse getUserById(String name) {
        return null;
    }

    @Override
    public APIResponse checkUserName(String name) {
        return null;
    }
}
