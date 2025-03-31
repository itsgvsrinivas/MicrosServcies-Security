package com.gv.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerDetails {
    private Long id;
    private String userName;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private String role;
}
