package com.mtvs.devlinkbackend.team.controller;

import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.service.TeamViewService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
public class TeamQueryController {
    private final JwtUtil jwtUtil;
    private final TeamViewService teamViewService;

    public TeamQueryController(JwtUtil jwtUtil, TeamViewService teamViewService) {
        this.jwtUtil = jwtUtil;
        this.teamViewService = teamViewService;
    }

    @Operation(summary = "ID로 팀 조회", description = "ID를 기반으로 팀 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamSingleReponseDTO> getTeamById(
            @PathVariable Long teamId) {

        TeamSingleReponseDTO team = teamViewService.findTeamByTeamId(teamId);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "사용자가 멤버인 팀 조회", description = "사용자가 멤버인 팀 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 조회되었습니다.")
    })
    @GetMapping("/teamlist")
    public ResponseEntity<TeamListResponseDTO> getTeamsByMemberIdContaining(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        TeamListResponseDTO teams = teamViewService.findByAccountIdInTeam(accountId);
        return ResponseEntity.ok(teams);
    }
}
