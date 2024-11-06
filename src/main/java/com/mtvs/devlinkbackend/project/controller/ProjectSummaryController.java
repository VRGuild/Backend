package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryPagingResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectSummaryViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/summary")
public class ProjectSummaryController {
    private final ProjectSummaryViewService projectSummaryViewService;

    public ProjectSummaryController(ProjectSummaryViewService projectSummaryViewService) {
        this.projectSummaryViewService = projectSummaryViewService;
    }

    @Operation(summary = "프로젝트 요약 목록 조회", description = "페이지 번호에 맞는 프로젝트 요약 목록을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 프로젝트 요약 목록을 가져왔습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "프로젝트 요약 목록을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @GetMapping("/{page}")
    public ResponseEntity<ProjectSummaryPagingResponseDTO> getProjectSummaries(
            @PathVariable(name = "page") int page) {
        ProjectSummaryPagingResponseDTO responseDTO = projectSummaryViewService.findAllProjectSummaryWithPagination(page);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "홈 화면용 프로젝트 요약 목록 조회", description = "홈 화면에 표시할 프로젝트 요약 목록을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 프로젝트 요약 목록을 가져왔습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "프로젝트 요약 목록을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @GetMapping("/home")
    public ResponseEntity<ProjectSummaryPagingResponseDTO> getProjectPreviewsInHome() {
        ProjectSummaryPagingResponseDTO responseDTO = projectSummaryViewService.findAllProjectPreviewInHome();
        return ResponseEntity.ok(responseDTO);
    }
}
