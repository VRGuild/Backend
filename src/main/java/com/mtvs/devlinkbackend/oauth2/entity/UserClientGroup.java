package com.mtvs.devlinkbackend.oauth2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "USER_CLIENT_GROUP")
@Entity(name = "UserClientGroup")
@DiscriminatorValue("UserClientGroup") // purpose 값으로 지정
@Getter
public class UserClientGroup extends User {
    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "GROUP_NAME") // 회사명/팀명
    private String groupName;

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "MANAGER_PHONE")
    private String managerPhone;
}
