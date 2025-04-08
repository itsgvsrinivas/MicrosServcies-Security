package com.gv.user_service.controller;

import com.gv.user_service.dto.request.Login;
import com.gv.user_service.dto.request.RegisterCustomer;
import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.dto.response.GetCustomerDetails;
import com.gv.user_service.security.JwtService;
import com.gv.user_service.service.CustomerService;
import com.gv.user_service.util.Constants;
import com.gv.user_service.util.ResponseUtils;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customers/api/v1")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping("/test")
    public String testApi() {
        return "Hello World test API";
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerCustomer(@RequestBody RegisterCustomer registerCustomer) {
        log.info("[CustomerController] >> [registerCustomer]");
        boolean isSuccess = customerService.register(registerCustomer);
        APIResponse apiResponse = null;
        if (isSuccess)
            apiResponse = ResponseUtils.createApiResponse(true, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, "", null);
        else
            apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, Constants.STATUS_DESC_FAILURE, "", null);
        return new ResponseEntity<>(apiResponse, isSuccess ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/customer")
    public ResponseEntity<APIResponse> updateCustomer(@RequestBody RegisterCustomer registerCustomer) {
        log.info("[CustomerController] >> [updateCustomer]");
        boolean isSuccess = customerService.updateCustomer(registerCustomer);
        APIResponse apiResponse = null;
        if (isSuccess)
            apiResponse = ResponseUtils.createApiResponse(true, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, "", null);
        else
            apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, Constants.STATUS_DESC_FAILURE, "", null);
        return new ResponseEntity<>(apiResponse, isSuccess ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getCustomerById(@PathVariable Long id) {
        log.info("[CustomerController] >> [getCustomerById]: {}", id);
        GetCustomerDetails getCustomerDetails = customerService.getCustomerById(id);
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, getCustomerDetails, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<APIResponse> deleteCustomerById(@PathVariable Long id) {
        log.info("[CustomerController] >> [deleteCustomerById]: {}", id);
        boolean isSuccess = customerService.deleteCustomerById(id);
        APIResponse apiResponse = ResponseUtils.createApiResponse(isSuccess, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, isSuccess, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<APIResponse> deleteCustomerByEmail(@PathVariable Long id, @RequestParam String email) {
        log.info("[CustomerController] >> [deleteCustomerByEmail]: {}", email);
        boolean isSuccess = customerService.deleteCustomerByEmail(email);
        APIResponse apiResponse = ResponseUtils.createApiResponse(isSuccess, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, isSuccess, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("customers")
    public ResponseEntity<APIResponse> getAllCustomers() {
        log.info("[CustomerController] >> [getAllCustomers]");
        List<GetCustomerDetails> getCustomerDetails = customerService.getAllCustomers();
        APIResponse apiResponse = ResponseUtils.createApiResponse(true, Constants.STATUS_CODE_SUCCESS, Constants.STATUS_DESC_SUCCESS, getCustomerDetails, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<APIResponse> login(@RequestBody Login login) {
        log.info("[CustomerController] >> [login] :{}", login.toString());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        boolean isAuthenticated = authentication.isAuthenticated();
        if (isAuthenticated) {
            String token = jwtService.generateAccessToken(null);
        }
        log.info("[CustomerController] >> [login] isAuthenticated:{}", isAuthenticated);
        APIResponse apiResponse = ResponseUtils.createApiResponse(isAuthenticated,
                isAuthenticated ? Constants.STATUS_CODE_SUCCESS : Constants.STATUS_CODE_FAILURE,
                isAuthenticated ? Constants.STATUS_DESC_SUCCESS : Constants.STATUS_DESC_FAILURE,
                isAuthenticated, null);
        return new ResponseEntity<>(apiResponse, isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @PreDestroy
    public void onShutDown() {
        // Cleanup logic here
        log.info("onShutDown++++");
    }
}
