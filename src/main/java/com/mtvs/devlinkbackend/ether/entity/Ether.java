package com.mtvs.devlinkbackend.ether.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "ETHER")
@Entity(name = "Ether")
@Getter
@ToString
public class Ether {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETHER_ID")
    private Long etherId;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "REASON")
    private String reason;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    public Ether () {}

    public Ether(String accountId, String reason, Long amount) {
        this.accountId = accountId;
        this.reason = reason;
        this.amount = amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
