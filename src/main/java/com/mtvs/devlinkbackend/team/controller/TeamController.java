package com.mtvs.devlinkbackend.team.controller;

import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TeamSingleReponseDTO> registTeam(
            @RequestBody TeamRegistRequestDTO teamRegistRequestDTO, @RequestParam String accountId) {

        TeamSingleReponseDTO team = teamService.registTeam(teamRegistRequestDTO, accountId);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @Operation(summary = "ID로 팀 조회", description = "ID를 기반으로 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamSingleReponseDTO> getTeamById(
            @PathVariable Long teamId) {

        TeamSingleReponseDTO team = teamService.findTeamByTeamId(teamId);
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
    public ResponseEntity<TeamListResponseDTO> getTeamsByPmId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String pmId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        TeamListResponseDTO teams = teamService.findTeamsByPmId(pmId);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "팀 이름으로 팀 조회", description = "지정된 문자열이 포함된 팀 이름을 가진 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<TeamListResponseDTO> getTeamsByTeamNameContaining(
            @RequestParam String teamName) {

        TeamListResponseDTO teams = teamService.findTeamsByTeamNameContaining(teamName);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "사용자가 멤버인 팀 조회", description = "사용자가 멤버인 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다.")
    })
    @GetMapping("/members/search")
    public ResponseEntity<TeamListResponseDTO> getTeamsByMemberIdContaining(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String memberId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        TeamListResponseDTO teams = teamService.findTeamsByMemberIdContaining(memberId);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "팀 업데이트", description = "기존 팀을 업데이트하고 업데이트된 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터 또는 권한이 없는 접근입니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<TeamSingleReponseDTO> updateTeam(
            @RequestBody TeamUpdateRequestDTO teamUpdateRequestDTO,
            @RequestParam String accountId) {

        try {
            TeamSingleReponseDTO updatedTeam = teamService.updateTeam(teamUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedTeam);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "팀 멤버 추가", description = "길드에 멤버를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 추가되었습니다."),
            @ApiResponse(responseCode = "403", description = "추가 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @PostMapping("/member")
    public TeamSingleReponseDTO addMemberToTeam(
            @RequestBody TeamMemberModifyRequestDTO teamMemberModifyRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return teamService.addMemberToTeam(teamMemberModifyRequestDTO, accountId);
    }

    @Operation(summary = "팀 멤버 제거", description = "길드에서 멤버를 제거합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 제거되었습니다."),
            @ApiResponse(responseCode = "403", description = "제거 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @DeleteMapping("/member")
    public TeamSingleReponseDTO removeMemberFromTeam(
            @RequestBody TeamMemberModifyRequestDTO teamMemberModifyRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return teamService.removeMemberToTeam(teamMemberModifyRequestDTO, accountId);
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
