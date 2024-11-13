package com.mtvs.devlinkbackend.user.command.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "BUSINESS")
@Entity(name = "Business")
@Getter @Setter
@NoArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "BUSINESS_LOGO_URL", columnDefinition="TEXT")
    private String businessLogoUrl;

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "MANAGER_PHONE")
    private String managerPhone;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "USER_ID")
    private Long userId;

    public Business(String businessName, String businessLogoUrl, String managerName, String managerPhone, Long userId) {
        this.businessName = businessName;
        this.businessLogoUrl = businessLogoUrl;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.userId = userId;
    }
}
