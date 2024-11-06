package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndUserAndTeamIdListDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDetailSingleResponseDTO {
    private ProjectAndUserAndTeamIdListDTO data;
}
