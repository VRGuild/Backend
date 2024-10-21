package com.mtvs.devlinkbackend.project.controller;

import com.mtvs.devlinkbackend.project.dto.ProjectPagingResponseDTO;
import com.mtvs.devlinkbackend.project.dto.ProjectVectorRegistRequestDTO;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.project.dto.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    public ProjectController(ProjectService projectService, JwtUtil jwtUtil) {
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "새로운 프로젝트 의뢰 등록", description = "새로운 프로젝트 의뢰를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "프로젝트 의뢰가 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    @PostMapping
    public ResponseEntity<Project> registerProject(
            @RequestBody ProjectRegistRequestDTO requestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        Project newProject = projectService.registProject(requestDTO, accountId);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @Operation(summary = "특정 프로젝트 의뢰 조회", description = "ID로 특정 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "프로젝트 의뢰를 찾을 수 없음")
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.findProjectByProjectId(projectId);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Pagination으로 계정별 프로젝트 의뢰 목록 조회", description = "Pagination으로 특정 계정에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/account")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByAccountId(
            @RequestParam int page,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        ProjectPagingResponseDTO projects = projectService.findProjectsByAccountIdWithPaging(page, accountId);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 업무 범위별 프로젝트 의뢰 목록 조회", description = "Pagination으로 특정 업무 범위에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/work-scope")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByWorkScope(
            @RequestParam int page,
            @RequestParam String workScope) {

        ProjectPagingResponseDTO projects = projectService.findProjectsByWorkScopeWithPaging(page, workScope);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 근무 형태별 프로젝트 의뢰 목록 조회", description = "Pagination으로 근무 형태 범위에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/work-type")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByWorkType(
            @RequestParam int page,
            @RequestParam String workType) {

        ProjectPagingResponseDTO projects =
                projectService.findProjectsByWorkTypeWithPaging(page, workType);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 진행 분류별 프로젝트 의뢰 목록 조회", description = "Pagination으로 진행 분류 범위에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/progress-classfication")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByProgressClassification(
            @RequestParam int page,
            @RequestParam String progressClassification) {

        ProjectPagingResponseDTO projects =
                projectService.findProjectsByProgressClassificationWithPaging(page, progressClassification);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 프로젝트 주제(제목)별 프로젝트 의뢰 목록 조회", description = "Pagination으로 프로젝트 주제(제목) 범위에 대한 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/title")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByTitleContainingIgnoreCase(
            @RequestParam int page,
            @RequestParam String title) {

        ProjectPagingResponseDTO projects =
                projectService.findProjectsByTitleContainingIgnoreCaseWithPaging(page, title);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 특정 기간 내의 프로젝트 의뢰 목록 조회", description = "Pagination으로 시작날짜 또는 끝 날짜를 포함하는 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/date-range")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsBetweenDates(
            @RequestParam int page,
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime) {

        ProjectPagingResponseDTO projects =
                projectService.findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqualWithPaging(
                        page, startDateTime, endDateTime);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Pagination으로 원하는 직군별 프로젝트 의뢰 목록 조회", description = "Pagination으로 원하는 직군 숫자보다 많이 모집하는 모든 프로젝트 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/required")
    public ResponseEntity<ProjectPagingResponseDTO> getProjectsByTitleContainingIgnoreCase(
            @RequestParam int page, @RequestParam int requiredClient, @RequestParam int requiredServer,
            @RequestParam int requiredDesign, @RequestParam int requiredPlanner, @RequestParam int requiredAIEngineer) {

        ProjectPagingResponseDTO projects = projectService.findProjectsWithLargerRequirementsWithPaging(
                page, requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer
        );
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "PK로 프로젝트 의뢰의 Vector 정보 조회", description = "PK에 맞는 프로젝트가 가진 Vector 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vector 정보가 성공적으로 조회됨")
    })
    @GetMapping("/{projectId}/vector")
    public ResponseEntity<Map<String, Integer>> getProjectVectorByProjectId(
            @PathVariable(name = "projectId") Long projectId) {

        Map<String, Integer> foundProjectVector = projectService.findProjectVectorByProjectId(projectId);
        return ResponseEntity.ok(foundProjectVector);
    }


    @Operation(summary = "프로젝트 의뢰 업데이트", description = "기존 프로젝트 의뢰를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 또는 권한 없음")
    })
    @PatchMapping
    public ResponseEntity<Project> updateProject(
            @RequestBody ProjectUpdateRequestDTO requestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) {

        try {
            String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
            Project updatedProject = projectService.updateProject(requestDTO, accountId);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "프로젝트 의뢰 업데이트", description = "기존 프로젝트 의뢰를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로젝트 의뢰가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 또는 권한 없음")
    })
    @PatchMapping("/vector")
    public ResponseEntity<Project> updateProject(
            @RequestBody ProjectVectorRegistRequestDTO projectVectorRegistRequestDTO) {

        try {
            Project updatedProject = projectService.updateProjectVector(projectVectorRegistRequestDTO);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
