package com.mtvs.devlinkbackend.project.entity;

import com.mtvs.devlinkbackend.common.util.converter.LongListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "PROJECT")
@Entity(name = "Project")
@NoArgsConstructor
@ToString
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "WORK_TYPE")
    private String workType;

    @Column(name = "PROGRESS_CLASSIFICATION")
    private String progressClassification;

    @ElementCollection
    @CollectionTable(name = "REQUIRED_OCUUPATION_LIST", joinColumns = @JoinColumn(name = "PROJECT_ID"))
    private List<Occupation> requiredOccupationList;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "ESTIMATED_COST")
    private Integer estimatedCost;

    @Convert(converter = LongListConverter.class)
    @Column(name = "COMMENT_ID_LIST")
    private List<Long> commentIdList;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Project(Long userId, String title, String content, String workType, String progressClassification, List<Occupation> requiredOccupationList, LocalDate startDate, LocalDate endDate, Integer estimatedCost) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.workType = workType;
        this.progressClassification = progressClassification;
        this.requiredOccupationList = requiredOccupationList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedCost = estimatedCost;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setProgressClassification(String progressClassification) {
        this.progressClassification = progressClassification;
    }

    public void setRequiredOccupationList(List<Occupation> requiredOccupationList) {
        this.requiredOccupationList = requiredOccupationList;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEstimatedCost(Integer estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public void setCommentIdList(List<Long> commentIdList) {
        this.commentIdList = commentIdList;
    }
}
