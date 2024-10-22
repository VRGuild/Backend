package com.mtvs.devlinkbackend.guild.controller;

import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.guild.service.GuildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guild")
public class GuildController {

    private final GuildService guildService;
    private final JwtUtil jwtUtil;

    public GuildController(GuildService guildService, JwtUtil jwtUtil) {
        this.guildService = guildService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "길드 생성", description = "새로운 길드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "길드 생성 요청이 잘못되었습니다.")
    })
    @PostMapping
    public GuildSingleResponseDTO createGuild(
            @RequestBody GuildRegistRequestDTO guildRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.createGuild(guildRegistRequestDTO, accountId);
    }

    @Operation(summary = "길드 조회", description = "길드 ID로 길드를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @GetMapping("/{guildId}")
    public GuildSingleResponseDTO findGuildByGuildId(@PathVariable Long guildId) {
        return guildService.findGuildByGuildId(guildId);
    }

    @Operation(summary = "길드 이름 검색", description = "이름이 특정 문자열을 포함하는 길드 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/search")
    public GuildListResponseDTO findGuildsByGuildNameContaining(@RequestParam String guildName) {
        return guildService.findGuildsByGuildNameContaining(guildName);
    }

    @Operation(summary = "사용자가 소유자인 길드 조회", description = "사용자가 소유자인 길드를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/owner")
    public GuildListResponseDTO findGuildsByOwnerId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String ownerId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.findGuildsByOwnerId(ownerId);
    }

    @Operation(summary = "사용자가 멤버인 길드 조회", description = "사용자가 멤버인 길드를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "길드 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/member")
    public GuildListResponseDTO findGuildsByMemberIdContaining(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String memberId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.findGuildsByMemberIdContaining(memberId);
    }

    @Operation(summary = "길드 수정", description = "길드 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "403", description = "수정 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @PatchMapping
    public GuildSingleResponseDTO updateGuild(
            @RequestBody GuildUpdateRequestDTO guildUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.updateGuild(guildUpdateRequestDTO, accountId);
    }

    @Operation(summary = "길드 멤버 추가", description = "길드에 멤버를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 추가되었습니다."),
            @ApiResponse(responseCode = "403", description = "추가 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @PostMapping("/member")
    public GuildSingleResponseDTO addMemberToGuild(
            @RequestBody GuildMemberModifyRequestDTO guildMemberModifyRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.addMemberToGuild(guildMemberModifyRequestDTO, accountId);
    }

    @Operation(summary = "길드 멤버 제거", description = "길드에서 멤버를 제거합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 제거되었습니다."),
            @ApiResponse(responseCode = "403", description = "제거 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @DeleteMapping("/member")
    public GuildSingleResponseDTO removeMemberFromGuild(
            @RequestBody GuildMemberModifyRequestDTO guildMemberModifyRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return guildService.removeMemberToGuild(guildMemberModifyRequestDTO, accountId);
    }

    @Operation(summary = "길드 삭제", description = "길드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{guildId}")
    public void deleteGuild(@PathVariable Long guildId) {
        guildService.deleteGuild(guildId);
    }
}

