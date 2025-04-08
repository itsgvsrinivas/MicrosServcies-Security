package com.gv.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "account_number", unique = true, nullable = false, length = 20)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt = new Date();
}
