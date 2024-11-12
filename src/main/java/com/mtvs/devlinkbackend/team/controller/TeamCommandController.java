package com.mtvs.devlinkbackend.team.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
public class TeamCommandController {

    private final TeamService teamService;

    private final JwtUtil jwtUtil;

    public TeamCommandController(TeamService teamService, JwtUtil jwtUtil) {
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
            @RequestBody TeamRegistRequestDTO teamRegistRequestDTO) {

        TeamSingleReponseDTO team = teamService.registTeam(teamRegistRequestDTO);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @Operation(summary = "팀 업데이트", description = "기존 팀을 업데이트하고 업데이트된 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터 또는 권한이 없는 접근입니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<TeamSingleReponseDTO> updateTeam(
            @RequestBody TeamUpdateRequestDTO teamUpdateRequestDTO) {

        try {
            TeamSingleReponseDTO updatedTeam = teamService.updateTeam(teamUpdateRequestDTO);
            return ResponseEntity.ok(updatedTeam);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "팀 멤버 지원", description = "팀에 멤버로 지원합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 추가되었습니다."),
            @ApiResponse(responseCode = "403", description = "추가 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping("/apply")
    public ResponseEntity<TeamSingleReponseDTO> addMemberToTeam(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TeamMemberModifyRequestDTO teamMemberModifyRequestDTO) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        TeamSingleReponseDTO updatedTeam = teamService.applyMemberToTeam(teamMemberModifyRequestDTO, accountId);
        return updatedTeam != null ? ResponseEntity.ok(updatedTeam) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "팀에서 멤버 제거 ", description = "길드에서 멤버를 제거합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 제거되었습니다."),
            @ApiResponse(responseCode = "403", description = "제거 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping("/remove")
    public ResponseEntity<TeamSingleReponseDTO> removeMemberFromTeam(
            @RequestBody TeamMemberModifyRequestDTO teamMemberModifyRequestDTO) throws Exception {

        TeamSingleReponseDTO updatedTeam = teamService.removeMemberToTeam(teamMemberModifyRequestDTO);
        return updatedTeam != null ? ResponseEntity.ok(updatedTeam) : ResponseEntity.notFound().build();
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
