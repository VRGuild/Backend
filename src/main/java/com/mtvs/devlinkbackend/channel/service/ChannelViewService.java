package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.repository.ChannelViewRepository;
import org.springframework.stereotype.Service;

@Service
public class ChannelViewService {

    private final ChannelViewRepository channelViewRepository;

    public ChannelViewService(ChannelViewRepository channelViewRepository) {
        this.channelViewRepository = channelViewRepository;
    }

    public ChannelSingleResponseDTO findChannelInitInfoByChannelId(String channelId) {
        Channel channel = channelViewRepository.findById(channelId).orElse(null);

        return new ChannelSingleResponseDTO(channel);
    }
}
