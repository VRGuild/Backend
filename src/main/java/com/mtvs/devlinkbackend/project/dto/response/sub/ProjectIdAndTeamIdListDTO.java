package com.mtvs.devlinkbackend.project.dto.response.sub;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectIdAndTeamIdListDTO {
    private Long projectId;
    private List<Long> supportTeamIdList;
}
