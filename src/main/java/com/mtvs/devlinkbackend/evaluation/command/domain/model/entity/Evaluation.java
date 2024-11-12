package com.mtvs.devlinkbackend.evaluation.command.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "ESTIMATOR_ID")
    private Long estimatorId;

    @Column(name = "ESTIMATEDER_ID")
    private Long estimatederId;

    @Column(name = "CAUSE")
    private String cause;

    @Column(name = "POINT")
    private Integer point;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT", updatable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKILL_CATEGORY_INFO_ID", nullable = false)
    @JsonIgnore
    private SkillCategoryInfo skillCategoryInfo;

    public Evaluation(Long estimatorId, Long estimatederId, String cause, Integer point, SkillCategoryInfo skillCategoryInfo) {
        this.estimatorId= estimatorId;
        this.estimatederId = estimatederId;
        this.cause = cause;
        this.point = point;
        this.skillCategoryInfo = skillCategoryInfo;
    }
}
