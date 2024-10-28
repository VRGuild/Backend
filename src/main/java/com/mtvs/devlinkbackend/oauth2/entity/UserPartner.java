package com.mtvs.devlinkbackend.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mtvs.devlinkbackend.util.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Table(name = "USER_PARTNER")
@Entity(name = "UserPartner")
@DiscriminatorValue("UserPartner") // purpose 값으로 지정
@Getter @Setter
@NoArgsConstructor
public class UserPartner extends User {
    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "GITHUB_LINK")
    private String githubLink;

    @Convert(converter = StringListConverter.class)
    @Column(name = "PORTFOLIO_LIST", columnDefinition = "TEXT")
    private List<String> portfolioList;

    @Column(name = "EXPERIENCE", columnDefinition = "TEXT") // 경력 기술
    private String experience;

    @Column(name = "EXP_POINTS")
    private Long expPoints;

    @OneToMany(mappedBy = "userPartner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> skillSet;

    @OneToMany(mappedBy = "userPartner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Stat> statSet;

    @Column(name = "MESSAGE", columnDefinition = "TEXT") // 하고 싶은 말
    private String message;

    public UserPartner(String accountId, String purpose, String nickname, String name, String email, String phone,String githubLink, List<String> portfolioList, String experience, String message) {
        super(accountId, purpose);
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.githubLink = githubLink;
        this.portfolioList = portfolioList;
        this.experience = experience;
        this.message = message;
    }
}

