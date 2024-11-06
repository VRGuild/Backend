package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectIdAndTeamIdListDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectTeamResponseDTO {
    private ProjectIdAndTeamIdListDTO data;
}
