package com.gv.user_service.service;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.dto.request.RegisterCustomer;
import com.gv.user_service.dto.response.GetCustomerDetails;

import java.util.List;

public interface CustomerService {
    boolean register(RegisterCustomer registerCustomer);

    boolean updateCustomer(RegisterCustomer registerCustomer);

    APIResponse validateCustomer(String userId, String password);

    GetCustomerDetails getCustomerById(Long id);

    boolean deleteCustomerByEmail(String email);

    List<GetCustomerDetails> getAllCustomers();
}
