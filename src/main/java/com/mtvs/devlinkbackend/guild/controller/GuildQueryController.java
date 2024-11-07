package com.mtvs.devlinkbackend.guild.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailPagingResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.service.GuildViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guild")
public class GuildQueryController {
    private final GuildViewService guildViewService;
    private final JwtUtil jwtUtil;

    public GuildQueryController(GuildViewService guildViewService, JwtUtil jwtUtil) {
        this.guildViewService = guildViewService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "길드 조회", description = "길드 ID로 길드를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @GetMapping("/{guildId}")
    public ResponseEntity<GuildDetailSingleResponseDTO> findGuildByGuildId(@PathVariable Long guildId) {
        return ResponseEntity.ok(guildViewService.findGuildDetailByGuildId(guildId));
    }

    @Operation(summary = "길드 목록 조회", description = "이름이 특정 문자열을 포함하는 길드 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/list/{page}")
    public ResponseEntity<GuildDetailPagingResponseDTO> findGuildsWithPagination(@PathVariable Integer page) {
        return ResponseEntity.ok(guildViewService.findGuildDetailsWithPagination(page));
    }

    @Operation(summary = "사용자가 소유자인 길드 조회", description = "사용자가 소유자인 길드를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/owner")
    public ResponseEntity<GuildListResponseDTO> findGuildsByOwnerId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return ResponseEntity.ok(guildViewService.findGuildsByMasterAccountId(accountId));
    }

    @Operation(summary = "사용자가 멤버인 길드 조회", description = "사용자가 멤버인 길드를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/member")
    public ResponseEntity<GuildListResponseDTO> findGuildsByMemberIdContaining(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return ResponseEntity.ok(guildViewService.findGuildsByAccountIdInGuild(accountId));
    }
}
