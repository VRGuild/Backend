package com.mtvs.devlinkbackend.evaluation.query.controller;

import com.mtvs.devlinkbackend.evaluation.command.application.dto.response.EvaluationListReponseDTO;
import com.mtvs.devlinkbackend.evaluation.query.service.EvaluationViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationQueryController {

    private final EvaluationViewService evaluationViewService;

    public EvaluationQueryController(EvaluationViewService evaluationViewService) {
        this.evaluationViewService = evaluationViewService;
    }

    @Operation(summary = "특정 사용자의 특정 분야에 대한 평가 내용들 조회", description = "특정 사용자의 특정 분야에 대한 평가 내용들 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다.")
    @ApiResponse(responseCode = "404", description = "찾을 수 없습니다.")
    @GetMapping("/{userId}/{categoryName}")
    public ResponseEntity<EvaluationListReponseDTO> getEvaluationListByUserIdAndCategoryName(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "categoryName") String skillCategoryName) {

        EvaluationListReponseDTO evaluationListReponseDTO =
                evaluationViewService.
                        findEvaluationListByUserIdAndCategoryName(userId, skillCategoryName);

        if (evaluationListReponseDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(evaluationListReponseDTO, HttpStatus.OK);
    }
}
