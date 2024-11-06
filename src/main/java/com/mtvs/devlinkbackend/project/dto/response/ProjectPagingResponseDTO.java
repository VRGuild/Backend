package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndTeamIdListDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectPagingResponseDTO {
    private List<ProjectAndTeamIdListDTO> data;
    private Integer totalPages;
    private Long totalProjectCnt;
}
