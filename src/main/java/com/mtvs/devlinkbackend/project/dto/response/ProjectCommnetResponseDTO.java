package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectIdAndCommnetIdListDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectCommnetResponseDTO {
    private ProjectIdAndCommnetIdListDTO data;
}
