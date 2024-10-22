package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/summary")
public class ProjectSummaryController {
    private final ProjectSummaryService projectSummaryService;

    public ProjectSummaryController(ProjectSummaryService projectSummaryService) {
        this.projectSummaryService = projectSummaryService;
    }

    @Operation(summary = "전체 프로젝트 요약 조회", description = "전체 프로젝트 요약을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<ProjectSummaryResponseDTO> findAllSummary() {
        return ResponseEntity.ok(projectSummaryService.findAllWithoutCommentsAndContent());
    }
}
