package com.gv.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "Customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String name;
    private String email;
    private String mobile;
    private String role;
}
