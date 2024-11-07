package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.ChannelListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.request.ChannelUpdateRequestDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.repository.ChannelRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserViewService userViewService;

    public ChannelService(ChannelRepository channelRepository, UserViewService userViewService) {
        this.channelRepository = channelRepository;
        this.userViewService = userViewService;
    }

    // 새 채널 저장
    @Transactional
    public ChannelSingleResponseDTO saveChannel(ChannelRegistRequestDTO channelRegistRequestDTO, String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 에픽 계정으로 접근중");

        Channel channel = channelRepository.save(new Channel(
                user.getUserId(),
                channelRegistRequestDTO.getPositionTypes()
        ));

        user.getChannelList().add(channel.getChannelId());

        return new ChannelSingleResponseDTO(channel);
    }

    // 모든 채널 조회

    public ChannelListResponseDTO findAllChannels() {
        return new ChannelListResponseDTO(channelRepository.findAll());
    }

    // ID로 특정 채널 조회
    public ChannelSingleResponseDTO findChannelByChannelId(String channelId) {
        return new ChannelSingleResponseDTO(channelRepository.findById(channelId).orElse(null));
    }

    // ID로 채널 업데이트
    @Transactional
    public ChannelSingleResponseDTO updateChannel(ChannelUpdateRequestDTO channelUpdateRequestDTO, String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 에픽계정으로 접근중");

        Optional<Channel> channel = channelRepository.findById(channelUpdateRequestDTO.getChannelId());
        if (channel.isPresent()) {
            Channel foundChannel = channel.get();
            if(foundChannel.getUserId().equals(user.getUserId())) {
                foundChannel.setPositionTypes(channelUpdateRequestDTO.getPositionTypes());
                return new ChannelSingleResponseDTO(foundChannel);
            } else throw new IllegalArgumentException("주인이 아닌 다른 사용자 계정으로 채널 수정 시도중");
        } else throw new IllegalArgumentException("요청한 channelId로 해당 채널이 존재하지 않음");
    }

    // ID로 채널 삭제
    public void deleteChannel(String channelId) {
        Channel channel = channelRepository.findById(channelId).orElse(null);
        if(channel == null)
            throw new IllegalArgumentException("잘못된 ChannelId로 접근중");

        User user = userViewService.findUserByUserId(channel.getUserId()).getData();
        if (user == null)
            throw new IllegalArgumentException("잘못된 UserId로 접근중");

        user.getChannelList().remove(channelId);
        channelRepository.deleteById(channelId);
    }
}
