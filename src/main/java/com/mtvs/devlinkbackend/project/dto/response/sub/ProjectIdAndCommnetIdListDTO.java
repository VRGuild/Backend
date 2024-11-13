package com.mtvs.devlinkbackend.project.dto.response.sub;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectIdAndCommnetIdListDTO {
    private Long projectId;
    private List<Long> commnetIdList;
}
