package com.mtvs.devlinkbackend.guild.controller;

import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.service.GuildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guild")
public class GuildCommandController {

    private final GuildService guildService;

    public GuildCommandController(GuildService guildService) {
        this.guildService = guildService;
    }

    @Operation(summary = "길드 생성", description = "새로운 길드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "길드 생성 요청이 잘못되었습니다.")
    })
    @PostMapping
    public ResponseEntity<GuildSingleResponseDTO> createGuild(
            @RequestBody GuildRegistRequestDTO guildRegistRequestDTO) {

        return ResponseEntity.ok(guildService.createGuild(guildRegistRequestDTO));
    }

    @Operation(summary = "길드 수정", description = "길드 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "403", description = "수정 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<GuildSingleResponseDTO> updateGuild(
            @RequestBody GuildUpdateRequestDTO guildUpdateRequestDTO) {

        return ResponseEntity.ok(guildService.updateGuild(guildUpdateRequestDTO));
    }

    @Operation(summary = "길드 멤버 추가", description = "길드에 멤버를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 추가되었습니다."),
            @ApiResponse(responseCode = "403", description = "추가 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @PostMapping("/apply/{guildId}")
    public GuildDetailSingleResponseDTO addMemberToGuild(
            @RequestBody GuildMemberModifyRequestDTO guildMemberModifyRequestDTO) {

        return guildService.applyMemberToGuild(guildMemberModifyRequestDTO);
    }

    @Operation(summary = "길드 삭제", description = "길드를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "길드가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "길드를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{guildId}")
    public ResponseEntity<Void> deleteGuild(@PathVariable Long guildId) {

        guildService.deleteGuild(guildId);
        return ResponseEntity.ok().build();
    }
}

