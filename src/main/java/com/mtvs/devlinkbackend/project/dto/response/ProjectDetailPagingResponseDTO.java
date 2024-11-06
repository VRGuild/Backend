package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndUserAndTeamIdListDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDetailPagingResponseDTO {
    private List<ProjectAndUserAndTeamIdListDTO> data;
    private Integer totalPages;
    private Long totalProjectCnt;
}
