package com.mtvs.devlinkbackend.user.command.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mtvs.devlinkbackend.evaluation.command.model.entity.Evaluation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Table(name = "SKILL_CATEGORY_INFO")
@Entity(name = "SkillCategoryInfo")
@Getter
@Setter
@ToString(exclude = "dev")
@NoArgsConstructor
public class SkillCategoryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_INFO_ID")
    private Long categoryInfoId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "IS_EVALUATED")
    private Boolean isEvaluated;

    @Column(name = "POINT_AVG")
    private Integer pointAvg;

    @OneToMany(mappedBy = "skillCategoryInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Evaluation> evaluationIdList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEV_ID", nullable = false)
    @JsonIgnore
    private Dev dev;

    public SkillCategoryInfo(String categoryName, List<Evaluation> evaluationIdList) {
        this.categoryName = categoryName;
        this.isEvaluated = false;
        this.evaluationIdList = evaluationIdList;
    }
}

