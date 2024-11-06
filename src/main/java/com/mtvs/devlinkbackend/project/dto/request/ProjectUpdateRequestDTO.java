package com.mtvs.devlinkbackend.project.dto.request;

import com.mtvs.devlinkbackend.project.entity.Occupation;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectUpdateRequestDTO {
    private Long projectId;
    private Long userId;
    private String title;
    private String content;
    private Integer estimatedCost;
    private String progressClassification;
    private String workType;
    private List<Occupation> requiredOccupationList;
    private List<Long> commentIdList;
    private LocalDate startDate;
    private LocalDate endDate;
}