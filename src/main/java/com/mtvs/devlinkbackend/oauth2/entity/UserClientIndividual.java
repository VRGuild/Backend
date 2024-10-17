package com.mtvs.devlinkbackend.oauth2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "USER_CLIENT_INDIVIDUAL")
@Entity(name = "UserClientIndividual")
@DiscriminatorValue("UserClientIndividual") // purpose 값으로 지정
@Getter @Setter
public class UserClientIndividual extends User {
    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    public UserClientIndividual() {
    }

    public UserClientIndividual(Long userId, String accountId, String purpose, String name, String phone) {
        super(userId, accountId, purpose);
        this.name = name;
        this.phone = phone;
    }
}
