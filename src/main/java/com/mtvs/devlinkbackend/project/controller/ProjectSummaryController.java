package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectSummaryController {
    private final ProjectDetailService projectDetailService;

    public ProjectSummaryController(ProjectDetailService projectDetailService) {
        this.projectDetailService = projectDetailService;
    }

    @Operation(summary = "전체 프로젝트 요약 조회", description = "전체 프로젝트 요약을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @GetMapping("/summary")
    public ResponseEntity<ProjectSummaryResponseDTO> findAllSummary() {
        ProjectSummaryResponseDTO summary = projectDetailService.findAllProjectSummary();
        return summary != null ? ResponseEntity.ok(summary) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "전체 프로젝트 세부 내용 조회", description = "전체 프로젝트 세부 내용을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @GetMapping("/{projectId}/detail")
    public ResponseEntity<ProjectDetailResponseDTO> findProjectDetailByProjectId(
            @PathVariable(name = "projectId") Long projectId) {
        ProjectDetailResponseDTO detail = projectDetailService.findProjectDetailByProjectId(projectId);
        return detail != null ? ResponseEntity.ok(detail) : ResponseEntity.notFound().build();
    }
}
