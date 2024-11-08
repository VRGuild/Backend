package com.mtvs.devlinkbackend.evaluation.command.application.dto.response;

import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationSingleResponseDTO {
    private Evaluation data;
}
