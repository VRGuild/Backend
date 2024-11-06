package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.response.ProjectSingleResponseDTO;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.project.dto.request.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectTeamResponseDTO;
import com.mtvs.devlinkbackend.project.service.ProjectService;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectCommandController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    public ProjectCommandController(ProjectService projectService, JwtUtil jwtUtil) {
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "새로운 프로젝트 의뢰 등록", description = "새로운 프로젝트 의뢰를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "프로젝트 의뢰가 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    @PostMapping
    public ResponseEntity<ProjectSingleResponseDTO> registerProject(
            @RequestBody ProjectRegistRequestDTO requestDTO) {

        ProjectSingleResponseDTO newProject = projectService.registProject(requestDTO);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }


    @Operation(summary = "프로젝트 의뢰 업데이트", description = "기존 프로젝트 의뢰를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 또는 권한 없음")
    })
    @PatchMapping
    public ResponseEntity<ProjectSingleResponseDTO> updateProject(
            @RequestBody ProjectUpdateRequestDTO requestDTO) {

        try {
            ProjectSingleResponseDTO updatedProject = projectService.updateProject(requestDTO);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "프로젝트에 새로 팀을 만들어서 지원", description = "새로운 프로젝트 의뢰를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "프로젝트 의뢰가 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    @PostMapping("/apply/epic/{projectId}")
    public ResponseEntity<ProjectTeamResponseDTO> applyProjectByNewTeam(
            @PathVariable(name = "projectId") Long projectId,
            @RequestBody TeamRegistRequestDTO teamRegistRequestDTO) {

        ProjectTeamResponseDTO newProject = projectService.applyProjectByNewTeam(teamRegistRequestDTO, projectId);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @Operation(summary = "프로젝트 의뢰 삭제", description = "ID로 특정 프로젝트 의뢰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "프로젝트 의뢰가 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
