package com.mtvs.devlinkbackend.oauth2;

import jakarta.persistence.*;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ACCOUNT_ID", unique = true)
    private String accountId;
    @Column(name = "EMAIL")
    private String email;
    
}
