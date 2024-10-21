package com.mtvs.devlinkbackend.support.controller;

import com.mtvs.devlinkbackend.support.dto.SupportRegistRequestDTO;
import com.mtvs.devlinkbackend.support.entity.Support;
import com.mtvs.devlinkbackend.support.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {
    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    /**
     * 프로젝트 지원을 생성하는 API
     * @param supportRegistRequestDTO 지원 요청 데이터
     * @return 생성된 지원 정보
     */
    @Operation(summary = "프로젝트 지원 생성", description = "프로젝트에 대한 새로운 지원을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "지원이 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping
    public ResponseEntity<Support> createSupport(@RequestBody SupportRegistRequestDTO supportRegistRequestDTO) {
        Support createdSupport = supportService.createSupport(supportRegistRequestDTO);
        return new ResponseEntity<>(createdSupport, HttpStatus.CREATED);
    }

    /**
     * 프로젝트 ID로 지원 목록을 조회하는 API
     * @param projectId 프로젝트 ID
     * @return 프로젝트에 대한 지원 목록
     */
    @Operation(summary = "프로젝트 ID로 지원 조회", description = "특정 프로젝트에 대한 모든 지원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지원 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 프로젝트를 찾을 수 없음")
    })
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Support>> getSupportsByProjectId(@PathVariable Long projectId) {
        List<Support> supports = supportService.findSupportsByProjectId(projectId);
        return new ResponseEntity<>(supports, HttpStatus.OK);
    }

    /**
     * 팀 ID로 지원 목록을 조회하는 API
     * @param teamId 팀 ID
     * @return 해당 팀의 지원 목록
     */
    @Operation(summary = "팀 ID로 지원 조회", description = "특정 팀에 대한 모든 지원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지원 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 팀을 찾을 수 없음")
    })
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Support>> getSupportsByTeamId(@PathVariable Long teamId) {
        List<Support> supports = supportService.findSupportsByTeamId(teamId);
        return new ResponseEntity<>(supports, HttpStatus.OK);
    }

    /**
     * 지원을 삭제하는 API
     * @param supportId 삭제할 지원 ID
     */
    @Operation(summary = "지원 삭제", description = "지정된 ID의 지원을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "지원이 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "해당 지원을 찾을 수 없음")
    })
    @DeleteMapping("/{supportId}")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long supportId) {
        supportService.deleteSupport(supportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
