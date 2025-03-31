package com.gv.user_service.repository;

import com.gv.user_service.dto.request.RegisterCustomer;
import com.gv.user_service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findByUserName(String name);

    Optional<CustomerEntity> findByEmail(String email);
}
