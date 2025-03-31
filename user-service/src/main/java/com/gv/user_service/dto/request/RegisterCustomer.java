package com.gv.user_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterCustomer {
    private Long id;
    private String userName;
    private String name;
    private String email;
    private String mobile;
    private String role;
    private String password;
}
