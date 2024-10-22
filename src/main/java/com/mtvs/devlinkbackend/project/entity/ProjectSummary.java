package com.mtvs.devlinkbackend.project.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectSummary {
    private Long projectId;
    private String workScope;
    private String workType;
    private String progressClassification;
    private String companyName;
    private String title;
    private Integer requiredClient;
    private Integer requiredServer;
    private Integer requiredDesign;
    private Integer requiredPlanner;
    private Integer requiredAIEngineer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer estimatedCost;
    private String accountId;
    private LocalDateTime createdAt;
    private Map<String, Integer> projectVector;

    // Project 객체로부터 필드 값을 가져오는 생성자
    public ProjectSummary(Project project) {
        this.projectId = project.getProjectId();
        this.workScope = project.getWorkScope();
        this.workType = project.getWorkType();
        this.progressClassification = project.getProgressClassification();
        this.companyName = project.getCompanyName();
        this.title = project.getTitle();
        this.requiredClient = project.getRequiredClient();
        this.requiredServer = project.getRequiredServer();
        this.requiredDesign = project.getRequiredDesign();
        this.requiredPlanner = project.getRequiredPlanner();
        this.requiredAIEngineer = project.getRequiredAIEngineer();
        this.startDateTime = project.getStartDateTime();
        this.endDateTime = project.getEndDateTime();
        this.estimatedCost = project.getEstimatedCost();
        this.accountId = project.getAccountId();
        this.createdAt = project.getCreatedAt();
        this.projectVector = project.getProjectVector(); // Map<String, Integer> projectVector
    }
}
