package com.mtvs.devlinkbackend.team.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.team.dto.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;
    private final JwtUtil jwtUtil;

    public TeamController(TeamService teamService, JwtUtil jwtUtil) {
        this.teamService = teamService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "팀 등록", description = "새로운 팀을 등록하고 등록된 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "팀이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터입니다.")
    })
    @PostMapping
    public ResponseEntity<Team> registTeam(@RequestBody TeamRegistRequestDTO teamRegistRequestDTO, @RequestParam String accountId) {
        Team team = teamService.registTeam(teamRegistRequestDTO, accountId);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @Operation(summary = "ID로 팀 조회", description = "ID를 기반으로 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(
            @PathVariable Long teamId) {

        Team team = teamService.findTeamByTeamId(teamId);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "사용자가 프로젝트 매니저인 팀 조회", description = "사용자가 프로젝트 매니저인 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "주어진 프로젝트 매니저 ID에 대한 팀이 없습니다.")
    })
    @GetMapping("/manager")
    public ResponseEntity<List<Team>> getTeamsByPmId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String pmId = jwtUtil.getSubjectFromTokenWithoutAuth(authorizationHeader);
        List<Team> teams = teamService.findTeamsByPmId(pmId);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "팀 이름으로 팀 조회", description = "지정된 문자열이 포함된 팀 이름을 가진 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Team>> getTeamsByTeamNameContaining(
            @RequestParam String teamName) {

        List<Team> teams = teamService.findTeamsByTeamNameContaining(teamName);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "사용자가 멤버인 팀 조회", description = "사용자가 멤버인 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다.")
    })
    @GetMapping("/members/search")
    public ResponseEntity<List<Team>> getTeamsByMemberIdContaining(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String memberId = jwtUtil.getSubjectFromTokenWithoutAuth(authorizationHeader);
        List<Team> teams = teamService.findTeamsByMemberIdContaining(memberId);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "팀 업데이트", description = "기존 팀을 업데이트하고 업데이트된 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터 또는 권한이 없는 접근입니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Team> updateTeam(
            @RequestBody TeamUpdateRequestDTO teamUpdateRequestDTO,
            @RequestParam String accountId) {

        try {
            Team updatedTeam = teamService.updateTeam(teamUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedTeam);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "팀 삭제", description = "ID를 통해 팀을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "팀이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
