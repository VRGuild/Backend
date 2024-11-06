package com.mtvs.devlinkbackend.project.repository.projection;

import com.mtvs.devlinkbackend.project.entity.Occupation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProjectSummary {
    Long getProjectId();
    Long getUserId();
    String getWorkType();
    String getProgressClassification();
    String getTitle();
    List<Occupation> getRequiredOccupationList();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Integer getEstimatedCost();
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();
}
