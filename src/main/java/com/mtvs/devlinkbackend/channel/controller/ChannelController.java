package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.ChannelUpdateRequestDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.service.ChannelService;
import com.mtvs.devlinkbackend.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Channel> createChannel(
            @RequestBody ChannelRegistRequestDTO channelRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        Channel savedChannel = channelService.saveChannel(channelRegistRequestDTO, accountId);
        return ResponseEntity.ok(savedChannel);
    }

    // 모든 채널 조회
    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.findAllChannels();
        return ResponseEntity.ok(channels);
    }

    // 특정 채널 조회
    @GetMapping("/{channelId}")
    public ResponseEntity<Channel> getChannelById(@PathVariable String channelId) {
        return channelService.findChannelByChannelId(channelId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 채널 업데이트
    @PatchMapping
    public ResponseEntity<Channel> updateChannel(
            @RequestBody ChannelUpdateRequestDTO channelUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        try {
            Channel updatedChannel = channelService.updateChannel(channelUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedChannel);
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
