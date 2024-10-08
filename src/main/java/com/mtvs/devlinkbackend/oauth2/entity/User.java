package com.mtvs.devlinkbackend.oauth2.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "USER")
@Entity(name = "USER")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ACCOUNT_ID", unique = true)
    private String accountId;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
    // 추후 추가 정보 필요시 Entity에 Column 추가 예정

    public User() {}

    public User(String accountId) {
        this.accountId = accountId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
