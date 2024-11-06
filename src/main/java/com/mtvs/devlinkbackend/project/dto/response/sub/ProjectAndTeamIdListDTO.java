package com.mtvs.devlinkbackend.project.dto.response.sub;

import com.mtvs.devlinkbackend.project.entity.Project;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectAndTeamIdListDTO {
    private Project projectInfo;
    private List<Long> supportTeamIdList;

    public ProjectAndTeamIdListDTO(Project projectInfo) {
        this.projectInfo = projectInfo;
    }
}
