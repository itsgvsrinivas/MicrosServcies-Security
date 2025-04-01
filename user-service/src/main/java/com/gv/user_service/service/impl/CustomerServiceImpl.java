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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

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
        if (customerEntityOptional.isEmpty()) {

            String encPwd = passwordEncoder.encode(registerCustomer.getPassword());
            registerCustomer.setPassword(encPwd);

            CustomerEntity customerEntity = new CustomerEntity();
            BeanUtils.copyProperties(registerCustomer, customerEntity);
            CustomerEntity savedUser = customerRepository.save(customerEntity);
            return savedUser.getId() != null;
        } else {
            throw new RuntimeException("Customer with same user name is already exist");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[CustomerServiceImpl] >> [loadUserByUsername] : {}", email);
        CustomerEntity customerEntity = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this email: " + email));
        return new User(customerEntity.getUserName(), customerEntity.getPassword(), Collections.emptyList());
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
    public boolean deleteCustomerByEmail(String email) throws ResourceNotFoundException {
        log.info("[CustomerServiceImpl] >> [deleteCustomerByEmail] : {}", email);
        CustomerEntity customerEntity = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this email:" + email));
        customerRepository.delete(customerEntity);
        return true;
    }

    @Override
    public boolean deleteCustomerById(Long id) throws ResourceNotFoundException {
        log.info("[CustomerServiceImpl] >> [deleteCustomerById] : {}", id);
        CustomerEntity customerEntity = customerRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this idd:" + id));
        customerRepository.delete(customerEntity);
        return true;
    }

    @Override
    public List<GetCustomerDetails> getAllCustomers() {
        List<CustomerEntity> customerEntityList = customerRepository.findAll();
        List<GetCustomerDetails> getCustomerDetailsList = customerEntityList.stream().map((customerEntity) -> {
            return GetCustomerDetails.builder()
                    .id(customerEntity.getId())
                    .userName(customerEntity.getUserName())
                    .password(customerEntity.getPassword())
                    .email(customerEntity.getEmail())
                    .role(customerEntity.getRole())
                    .name(customerEntity.getName())
                    .build();
        }).collect(Collectors.toList());
        return getCustomerDetailsList;
    }
}

//return new GetCustomerDetails(customerEntity.getId(), customerEntity.getName(), customerEntity.getUserName(), customerEntity.getEmail(), customerEntity.getMobile(), customerEntity.getRole(), customerEntity.getPassword());
