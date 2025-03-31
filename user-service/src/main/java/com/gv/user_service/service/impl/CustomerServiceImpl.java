package com.gv.user_service.service.impl;

import com.gv.user_service.dto.request.RegisterCustomer;
import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.dto.response.GetCustomerDetails;
import com.gv.user_service.entity.CustomerEntity;
import com.gv.user_service.exception.CustomerNotFoundException;
import com.gv.user_service.exception.ResourceNotFoundException;
import com.gv.user_service.repository.CustomerRepository;
import com.gv.user_service.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(RegisterCustomer registerCustomer) {
        //generate uniqueId everytime
        String randomUserId = UUID.randomUUID().toString();

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByUserName(registerCustomer.getUserName());
        if (customerEntityOptional.isPresent()) {
            throw new RuntimeException("Customer with same user name is already exist");
        }

        String encPwd = passwordEncoder.encode(registerCustomer.getPassword());
        registerCustomer.setPassword(encPwd);
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(registerCustomer, customerEntity);
        CustomerEntity savedUser = customerRepository.save(customerEntity);
        return savedUser.getUserName() != null;
    }

    @Override
    public boolean updateCustomer(RegisterCustomer registerCustomer) throws ResourceNotFoundException {
        int customerId = Math.toIntExact(registerCustomer.getId());
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id:" + customerId));

        customerEntity.setEmail(registerCustomer.getEmail());
        customerEntity.setMobile(registerCustomer.getMobile());
        customerEntity.setName(registerCustomer.getName());
        customerRepository.save(customerEntity);
        return false;
    }

    @Override
    public APIResponse validateCustomer(String userId, String password) {
        return null;
    }

    @Override
    public GetCustomerDetails getCustomerById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this id " + id));
        GetCustomerDetails getCustomerDetails = new GetCustomerDetails();
        getCustomerDetails.setId(customerEntity.getId());
        getCustomerDetails.setName(customerEntity.getName());
        getCustomerDetails.setMobile(customerEntity.getMobile());
        getCustomerDetails.setEmail(customerEntity.getEmail());
        getCustomerDetails.setUserName(customerEntity.getUserName());
        getCustomerDetails.setUserName(customerEntity.getRole());
        return getCustomerDetails;
    }

    @Override
    public boolean deleteCustomerByEmail(String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this email:" + email));
        customerRepository.delete(customerEntity);
        return true;
    }
}
