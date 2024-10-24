package com.mtvs.devlinkbackend.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Column(name = "WORK_SCOPE")
    private String workScope;

    @Column(name = "WORK_TYPE")
    private String workType;

    @Column(name = "PROGRESS_CLASSIFICATION")
    private String progressClassification;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "REQUIRED_CLIENT")
    private Integer requiredClient;

    @Column(name = "REQUIRED_SERVER")
    private Integer requiredServer;

    @Column(name = "REQUIRED_DESIGN")
    private Integer requiredDesign;

    @Column(name = "REQUIRED_PLANNER")
    private Integer requiredPlanner;

    @Column(name = "REQUIRED_AIENGINEER")
    private Integer requiredAIEngineer;

    @Column(name = "START_DATETIME")
    private LocalDate startDateTime;

    @Column(name = "END_DATETIME")
    private LocalDate endDateTime;

    @Column(name = "ESTIMATED_COST")
    private Integer estimatedCost;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private String accountId;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @ElementCollection
    @CollectionTable(name = "PROJECT_VECTOR", joinColumns = @JoinColumn(name = "PROJECT_ID"))
    @MapKeyColumn(name = "AXIS")
    @Column(name = "VALUE")
    private Map<String, Integer> projectVector;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    public Project(String title, String content, LocalDate startDateTime, LocalDate endDateTime, String accountId) {
        this.title = title;
        this.content = content;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.accountId = accountId;
    }

    public Project(String workScope, String workType, String progressClassification, String companyName, String title, String content, Integer requiredClient, Integer requiredServer, Integer requiredDesign, Integer requiredPlanner, Integer requiredAIEngineer, LocalDate startDateTime, LocalDate endDateTime, Integer estimatedCost, String accountId) {
        this.workScope = workScope;
        this.workType = workType;
        this.progressClassification = progressClassification;
        this.companyName = companyName;
        this.title = title;
        this.content = content;
        this.requiredClient = requiredClient;
        this.requiredServer = requiredServer;
        this.requiredDesign = requiredDesign;
        this.requiredPlanner = requiredPlanner;
        this.requiredAIEngineer = requiredAIEngineer;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.estimatedCost = estimatedCost;
        this.accountId = accountId;
    }

    public void setWorkScope(String workScope) {
        this.workScope = workScope;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setProgressClassification(String progressClassification) {
        this.progressClassification = progressClassification;
    }

    public void setRequiredClient(Integer requiredClient) {
        this.requiredClient = requiredClient;
    }

    public void setRequiredServer(Integer requiredServer) {
        this.requiredServer = requiredServer;
    }

    public void setRequiredDesign(Integer requiredDesign) {
        this.requiredDesign = requiredDesign;
    }

    public void setRequiredPlanner(Integer requiredPlanner) {
        this.requiredPlanner = requiredPlanner;
    }

    public void setRequiredAIEngineer(Integer requiredAIEngineer) {
        this.requiredAIEngineer = requiredAIEngineer;
    }

    public void setEstimatedCost(Integer estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setProjectVector(Map<String, Integer> projectVector) {
        this.projectVector = projectVector;
    }
}
