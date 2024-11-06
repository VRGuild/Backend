package com.mtvs.devlinkbackend.evaluation.command.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "EVALUATION")
@Entity(name = "Evaluation")
@Getter
@Setter
@ToString(exclude = "skillCategoryInfo")
@NoArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVALUATION_ID")
    private Long evaluationId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CAUSE")
    private String cause;

    @Column(name = "POINT")
    private Integer point;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKILL_CATEGORY_INFO_ID", nullable = false)
    @JsonIgnore
    private SkillCategoryInfo skillCategoryInfo;

    public Evaluation(Long userId, String cause, Integer point, LocalDateTime createdAt) {
        this.userId = userId;
        this.cause = cause;
        this.point = point;
        this.createdAt = createdAt;
    }
}
