package com.mtvs.devlinkbackend.experience.command.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "EXPERIENCE")
@Entity(name = "Experience")
@Getter
@NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXP_ID")
    private Long expId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CAUSE")
    private String cause;

    @Column(name = "AMOUNT")
    private Integer amount;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Experience(Long userId, String cause, Integer amount) {
        this.userId = userId;
        this.cause = cause;
        this.amount = amount;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
