package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.response.ProjectCommnetResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectTeamResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectQueryController {

    private final ProjectViewService projectViewService;

    public ProjectQueryController(ProjectViewService projectViewService) {
        this.projectViewService = projectViewService;
    }

    @Operation(summary = "특정 프로젝트 의뢰 조회", description = "ID로 특정 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectSingleResponseDTO> getProjectById(@PathVariable Long projectId) {
        ProjectSingleResponseDTO project = projectViewService.findProjectByProjectId(projectId);
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
    @GetMapping("/detail/{projectId}")
    public ResponseEntity<ProjectDetailSingleResponseDTO> getProjectDetailById(@PathVariable Long projectId) {
        ProjectDetailSingleResponseDTO project = projectViewService.findProjectDetailByProjectId(projectId);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "특정 프로젝트 의뢰 지원 팀 ID 조회", description = "ID로 특정 프로젝트 의뢰 지원 팀 ID를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @GetMapping("/team/{projectId}")
    public ResponseEntity<ProjectTeamResponseDTO> getProjectTeamById(@PathVariable Long projectId) {
        ProjectTeamResponseDTO project = projectViewService.findProjectTeamByProjectId(projectId);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "특정 프로젝트 의뢰 코멘트 ID 조회", description = "ID로 특정 프로젝트 의뢰 코멘트 ID를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @GetMapping("/comment/{projectId}")
    public ResponseEntity<ProjectCommnetResponseDTO> getProjectCommentById(@PathVariable Long projectId) {
        ProjectCommnetResponseDTO project = projectViewService.findProjectCommnetByProjectId(projectId);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
