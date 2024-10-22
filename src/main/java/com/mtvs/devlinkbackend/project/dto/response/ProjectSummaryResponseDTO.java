package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.entity.ProjectSummary;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectSummaryResponseDTO {
    private List<ProjectSummary> data;
}
