package com.mtvs.devlinkbackend.oauth2.entity;

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
@ToString(exclude = "user")
@Getter @Setter
@NoArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "BUSINESS_LOGO_URL")
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

    @OneToOne(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Business(String businessName, String businessLogoUrl, String managerName, String managerPhone, User user) {
        this.businessName = businessName;
        this.businessLogoUrl = businessLogoUrl;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.user = user;
    }
}
