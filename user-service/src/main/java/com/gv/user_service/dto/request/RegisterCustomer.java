package com.gv.user_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCustomer {
    private Long id;
    private String userName;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private String role;
}
