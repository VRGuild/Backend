package com.mtvs.devlinkbackend.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectVectorRegistRequestDTO {
    private Long projectId;
    @Schema(
            description = "프로젝트 벡터 정보. Key는 축 이름(X, Y, Z)이고, Value는 해당 축의 값입니다.",
            example = "{\"x\": 1, \"y\": 2, \"z\": 3}"
    )
    private Map<String, Integer> projectVector;
}
