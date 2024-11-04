package com.mtvs.devlinkbackend.user.command.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mtvs.devlinkbackend.util.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "DEV")
@Entity(name = "Dev")
@Getter @Setter
@ToString(exclude = "user")
@NoArgsConstructor
public class Dev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEV_ID")
    private Long devId;

    @Column(name = "DEV_NAME")
    private String devName;

    @Column(name = "DEV_EMAIL")
    private String devEmail;

    @Column(name = "DEV_PHONE")
    private String devPhone;

    @Column(name = "GITHUB_LINK")
    private String githubLink;

    @Convert(converter = StringListConverter.class)
    @Column(name = "PORTFOLIO_LIST", columnDefinition = "TEXT")
    private List<String> portfolioUrlList;

    @Column(name = "CAREER", columnDefinition = "TEXT") // 경력 기술
    private String career;

    @OneToMany(mappedBy = "dev", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SkillCategoryInfo> skillCategoryList;

    @Convert(converter = StringListConverter.class)
    @Column(name = "TAG", columnDefinition = "TEXT")
    private List<String> tag;

    @Column(name = "HOPE")
    private String hope;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "USER_ID")
    private Long userId;

    public Dev(String devName, String devEmail, String devPhone, String githubLink, List<String> portfolioUrlList, String career, List<String> tag, String hope, Long userId) {
        this.devName = devName;
        this.devEmail = devEmail;
        this.devPhone = devPhone;
        this.githubLink = githubLink;
        this.portfolioUrlList = portfolioUrlList;
        this.career = career;
        this.tag = tag;
        this.hope = hope;
        this.userId = userId;
    }
}

