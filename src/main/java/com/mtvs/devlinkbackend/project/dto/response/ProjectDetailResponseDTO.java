package com.mtvs.devlinkbackend.project.dto.response;

import com.mtvs.devlinkbackend.project.repository.projection.ProjectIdAndContent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDetailResponseDTO {
    private ProjectIdAndContent projectIdAndContent;
    private List<Long> commentIdList;
    private List<Long> supportedTeamIdList;
}
