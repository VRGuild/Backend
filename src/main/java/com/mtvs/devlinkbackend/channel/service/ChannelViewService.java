package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.ChannelListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.repository.ChannelViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChannelViewService {

    private final ChannelViewRepository channelViewRepository;

    private final Integer PAGE_SIZE = 15;

    public ChannelViewService(ChannelViewRepository channelViewRepository) {
        this.channelViewRepository = channelViewRepository;
    }

    public ChannelSingleResponseDTO findChannelInitInfoByChannelId(String channelId) {
        Channel channel = channelViewRepository.findById(channelId).orElse(null);

        return new ChannelSingleResponseDTO(channel);
    }

    public ChannelListResponseDTO findAllChannelInitInfo(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
        Page<Channel> channels = channelViewRepository.findAll(pageable);

        return new ChannelListResponseDTO(channels.getContent(), channels.getTotalPages());
    }
}
