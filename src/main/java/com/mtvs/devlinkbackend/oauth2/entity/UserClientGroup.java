package com.mtvs.devlinkbackend.oauth2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "USER_CLIENT_GROUP")
@Entity(name = "UserClientGroup")
@DiscriminatorValue("UserClientGroup") // purpose 값으로 지정
@Getter @Setter
public class UserClientGroup extends User {
    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "GROUP_NAME") // 회사명/팀명
    private String groupName;

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "MANAGER_PHONE")
    private String managerPhone;

    public UserClientGroup() {
    }

    public UserClientGroup(Long userId, String accountId, String purpose, String clientType, String groupName, String managerName, String managerPhone) {
        super(userId, accountId, purpose);
        this.clientType = clientType;
        this.groupName = groupName;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
    }
}
