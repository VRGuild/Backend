package com.mtvs.devlinkbackend.evaluation.command.application.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationUpdateRequestDTO {
    private Long evaluationId;
    private Long estimatorId;
    private Long estimatederId;
    private String cause;
    private Integer point;
}
