package com.mtvs.devlinkbackend.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "SKILL")
@Entity(name = "Skill")
@Getter
@Setter
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SKILL_ID")
    private Long skillId;

    @Column(name = "SKILL_CATEGORY")
    private String skillCategory;

    @Column(name = "SKILL_NAME")
    private String skillName;

    @Column(name = "SKILL_LEVEL")
    private Integer skillLevel;

    @ManyToOne
    @JoinColumn(name = "USER_PARTNER_ID", nullable = false)
    @JsonIgnore
    private UserPartner userPartner;

    public Skill(String skillCategory, String skillName, Integer skillLevel) {
        this.skillCategory = skillCategory;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }
}
