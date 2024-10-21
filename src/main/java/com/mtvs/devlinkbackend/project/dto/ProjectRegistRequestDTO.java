package com.mtvs.devlinkbackend.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectRegistRequestDTO {
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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer estimatedCost;
}