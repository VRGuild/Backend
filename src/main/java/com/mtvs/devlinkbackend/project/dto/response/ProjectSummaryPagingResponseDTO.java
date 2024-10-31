package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectSummaryPagingResponseDTO {
    private List<ProjectPreviewDTO> data;
    private Integer totalPages;
    private Long totalProjectCnt;
}
