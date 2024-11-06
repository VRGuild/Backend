package com.mtvs.devlinkbackend.project.dto.response.sub;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectPreviewDTO {
    private Long projectId;
    private String workType;
    private String progressClassification;
    private String previewTitle;
    private List<String> requiredOccupationNameList;
    private LocalDate deadlineDate;
}
