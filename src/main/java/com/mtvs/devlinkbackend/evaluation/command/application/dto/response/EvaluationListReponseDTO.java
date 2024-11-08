package com.mtvs.devlinkbackend.evaluation.command.application.dto.response;

import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationListReponseDTO {
    private List<Evaluation> data;
}
