package com.gv.user_service.dto.request;

import lombok.Data;

@Data
public class Login {
    private String email;
    private String password;
}
