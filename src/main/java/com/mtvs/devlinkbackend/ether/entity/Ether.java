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

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CAUSE")
    private String cause;

    @Column(name = "GOLD_AMOUNT")
    private Integer goldAmount;

    @Column(name = "SILVER_AMOUNT")
    private Integer silverAmount;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Ether () {}

    public Ether(Long userId, String cause, Integer goldAmount, Integer silverAmount) {
        this.userId = userId;
        this.cause = cause;
        this.goldAmount = goldAmount;
        this.silverAmount = silverAmount;
    }

    public void setGoldAmount(Integer goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void setSilverAmount(Integer silverAmount) {
        this.silverAmount = silverAmount;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
