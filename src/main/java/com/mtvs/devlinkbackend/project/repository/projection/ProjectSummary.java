package com.mtvs.devlinkbackend.project.repository.projection;

import java.time.LocalDateTime;
import java.util.Map;

public interface ProjectSummary {
    Long getProjectId();
    String getWorkScope();
    String getWorkType();
    String getProgressClassification();
    String getCompanyName();
    String getTitle();
    Integer getRequiredClient();
    Integer getRequiredServer();
    Integer getRequiredDesign();
    Integer getRequiredPlanner();
    Integer getRequiredAIEngineer();
    LocalDateTime getStartDateTime();
    LocalDateTime getEndDateTime();
    Integer getEstimatedCost();
    String getAccountId();
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();
    Map<String, Integer> getProjectVector();
}
