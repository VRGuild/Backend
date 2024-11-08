package com.mtvs.devlinkbackend.evaluation.command.application.controller;

import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationRegistRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationUpdateRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.response.EvaluationSingleResponseDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationCommandController {

    private final EvaluationService evaluationService;

    public EvaluationCommandController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Operation(summary = "SkillCategoryInfo Id로 평가 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<EvaluationSingleResponseDTO> registerEvaluation(
            @RequestBody EvaluationRegistRequestDTO evaluationRegistRequestDTO) {

        EvaluationSingleResponseDTO evaluationSingleResponseDTO =
                evaluationService.registerEvaluation(evaluationRegistRequestDTO);

        return evaluationSingleResponseDTO != null
                ? ResponseEntity.ok(evaluationSingleResponseDTO)
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "사용자가 내린 평가 내용 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<EvaluationSingleResponseDTO> update(
            @RequestBody EvaluationUpdateRequestDTO evaluationUpdateRequestDTO) {

        EvaluationSingleResponseDTO evaluationSingleResponseDTO =
                evaluationService.updateEvaluation(evaluationUpdateRequestDTO);
        return evaluationSingleResponseDTO != null
                ? ResponseEntity.ok(evaluationSingleResponseDTO)
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "평가 정보 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<Void> deleteByEvaluationId(
            @PathVariable Long evaluationId) {

        evaluationService.deleteById(evaluationId);
        return ResponseEntity.noContent().build();
    }
}
