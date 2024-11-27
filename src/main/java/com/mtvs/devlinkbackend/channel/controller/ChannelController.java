package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.service.ChannelService;
import com.mtvs.devlinkbackend.channel.service.ChannelViewService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelService channelService;
    private final JwtUtil jwtUtil;
    private final ChannelViewService channelViewService;

    public ChannelController(ChannelService channelService, JwtUtil jwtUtil, ChannelViewService channelViewService) {
        this.channelService = channelService;
        this.jwtUtil = jwtUtil;
        this.channelViewService = channelViewService;
    }

    @Operation(summary = "채널 등록", description = "새로운 채널을 생성하고 유저의 채널 리스트에 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "채널이 성공적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = ChannelSingleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ChannelSingleResponseDTO> registerChannel(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestBody ChannelRegistRequestDTO channelRegistRequestDTO) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        ChannelSingleResponseDTO responseDTO = channelService.registerChannelInitInfo(channelRegistRequestDTO, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "채널 정보 수정", description = "기존 채널의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채널 정보가 성공적으로 수정되었습니다.",
                    content = @Content(schema = @Schema(implementation = ChannelSingleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @PutMapping("/{channelId}")
    public ResponseEntity<ChannelSingleResponseDTO> updateChannel(
            @PathVariable String channelId,
            @RequestBody ChannelRegistRequestDTO channelRegistRequestDTO) {

        ChannelSingleResponseDTO responseDTO = channelService.updateChannelInitInfo(channelRegistRequestDTO, channelId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "채널 삭제", description = "특정 채널을 삭제하고 관련 데이터를 정리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "채널이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable String channelId) {
        channelService.deleteChannelByChannelId(channelId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "채널 정보 조회", description = "채널 ID를 기반으로 채널 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채널 정보를 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ChannelSingleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelSingleResponseDTO> getChannelInfo(@PathVariable String channelId) {
        ChannelSingleResponseDTO responseDTO = channelViewService.findChannelInitInfoByChannelId(channelId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "채널 정보 전체 조회", description = "채널 정보 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채널 정보 전체를 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ChannelSingleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/all/{page}")
    public ResponseEntity<ChannelListResponseDTO> getAllChannelInfo(@PathVariable int page) {

        ChannelListResponseDTO responseDTO = channelViewService.findAllChannelInitInfo(page);
        return ResponseEntity.ok(responseDTO);
    }
}
