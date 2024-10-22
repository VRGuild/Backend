package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.entity.Project;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectSingleResponseDTO {
    private Project data;
}
