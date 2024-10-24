package com.mtvs.devlinkbackend.oauth2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "USER")
@Entity(name = "User")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "purpose")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ACCOUNT_ID", unique = true)
    private String accountId;

    @Column(name = "PURPOSE", insertable = false, updatable = false) // "UserClient", "UserPartner" 둘 중 한개
    private String purpose;

    @Column(name = "CHARACTER_ID", unique = true)
    private Long characterId;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public User() {}

    public User(String accountId) {
        this.accountId = accountId;
    }

    public User(String accountId, String purpose) {
        this.userId = userId;
        this.accountId = accountId;
        this.purpose = purpose;
    }
}
