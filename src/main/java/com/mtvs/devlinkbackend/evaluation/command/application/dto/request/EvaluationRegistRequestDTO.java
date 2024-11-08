package com.mtvs.devlinkbackend.evaluation.command.application.dto.request;

import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationRegistRequestDTO {
    private Long categoryId;
    private Evaluation evaluationInfo;
}
