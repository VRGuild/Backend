package com.mtvs.devlinkbackend.oauth2.entity;

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

    @Convert(converter = StringListConverter.class)
    @Column(name = "PORTFOLIO_LIST", columnDefinition = "TEXT")
    private List<String> portfolioList;

    @Column(name = "EXPERIENCE", columnDefinition = "TEXT") // 경력 기술
    private String experience;

    @ElementCollection
    @CollectionTable(name = "SKILL", joinColumns = @JoinColumn(name = "USER_ID"))
    @MapKeyColumn(name = "SKILL_NAME")
    @Column(name = "SKILL_PROFICIENCY")
    private Map<String, Integer> skillSet;

    @Column(name = "MESSAGE", columnDefinition = "TEXT") // 하고 싶은 말
    private String messsage;

    public UserPartner(String accountId, String purpose, String nickname, String name, String email, String phone, List<String> portfolioList, String experience, Map<String, Integer> skillSet, String messsage) {
        super(accountId, purpose);
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.portfolioList = portfolioList;
        this.experience = experience;
        this.skillSet = skillSet;
        this.messsage = messsage;
    }


}

