package com.mtvs.devlinkbackend.project.dto;

import com.mtvs.devlinkbackend.project.entity.Project;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectPagingResponseDTO {
    private List<Project> data;
    private Integer totalPages;
}
