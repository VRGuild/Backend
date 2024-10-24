package com.mtvs.devlinkbackend.project.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectUpdateRequestDTO {
    private Long projectId;
    private String workScope;
    private String workType;
    private String progressClassification;
    private String companyName;
    private String title;
    private String content;
    private Integer requiredClient;
    private Integer requiredServer;
    private Integer requiredDesign;
    private Integer requiredPlanner;
    private Integer requiredAIEngineer;
    private LocalDate startDateTime;
    private LocalDate endDateTime;
    private Integer estimatedCost;
}