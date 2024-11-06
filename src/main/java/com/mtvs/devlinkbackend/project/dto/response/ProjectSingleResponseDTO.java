package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndTeamIdListDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectSingleResponseDTO {
    private ProjectAndTeamIdListDTO data;
}
