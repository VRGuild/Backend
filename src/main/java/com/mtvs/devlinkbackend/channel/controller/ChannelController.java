package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.response.ChannelListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.request.ChannelUpdateRequestDTO;
import com.mtvs.devlinkbackend.channel.service.ChannelService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelService channelService;
    private final JwtUtil jwtUtil;

    public ChannelController(ChannelService channelService, JwtUtil jwtUtil) {
        this.channelService = channelService;
        this.jwtUtil = jwtUtil;
    }

    // 새 채널 생성
    @PostMapping
    public ResponseEntity<ChannelSingleResponseDTO> createChannel(
            @RequestBody ChannelRegistRequestDTO channelRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        ChannelSingleResponseDTO savedChannel = channelService.saveChannel(channelRegistRequestDTO, accountId);
        return ResponseEntity.ok(savedChannel);
    }

    // 모든 채널 조회
    @GetMapping
    public ResponseEntity<ChannelListResponseDTO> getAllChannels() {
        ChannelListResponseDTO channels = channelService.findAllChannels();
        return ResponseEntity.ok(channels);
    }

    // 특정 채널 조회
    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelSingleResponseDTO> getChannelById(@PathVariable String channelId) {
        ChannelSingleResponseDTO channel = channelService.findChannelByChannelId(channelId);
        return channel != null ? ResponseEntity.ok(channel) : ResponseEntity.notFound().build();
    }

    // 채널 업데이트
    @PatchMapping
    public ResponseEntity<ChannelSingleResponseDTO> updateChannel(
            @RequestBody ChannelUpdateRequestDTO channelUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        try {
            ChannelSingleResponseDTO updatedChannelDTO = channelService.updateChannel(channelUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedChannelDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 채널 삭제
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable String channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.noContent().build();
    }
}
