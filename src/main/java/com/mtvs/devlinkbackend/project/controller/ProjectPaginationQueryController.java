package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailPagingResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectPagingResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectPaginationQueryController {
    private final JwtUtil jwtUtil;
    private final ProjectViewService projectViewService;

    public ProjectPaginationQueryController(JwtUtil jwtUtil, ProjectViewService projectViewService) {
        this.jwtUtil = jwtUtil;
        this.projectViewService = projectViewService;
    }

    @Operation(summary = "Pagination으로 계정별 프로젝트 의뢰 목록 조회", description = "Pagination으로 특정 계정에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/account")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByAccountId(@RequestParam int page) {

        ProjectPagingResponseDTO projects = projectViewService.findProjectsWithPagination(page);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "특정 프로젝트 의뢰 리스트 조회", description = "ID로 특정 프로젝트 의뢰 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @GetMapping("/list/{page}")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectById(@PathVariable Integer page) {
        ProjectPagingResponseDTO project = projectViewService.findProjectsWithPagination(page);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "특정 프로젝트 의뢰 세부 조회", description = "ID로 특정 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @GetMapping("/detail/list/{page}")
    public ResponseEntity<ProjectDetailPagingResponseDTO> getProjectDetailById(@PathVariable Integer page) {
        ProjectDetailPagingResponseDTO project = projectViewService.findProjectDetailsWithPagination(page);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
