package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.repository.ChannelRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final TileInfoService tileInfoService;
    private final ObjectInfoService objectInfoService;
    private final MemoService memoService;
    private final PdfService pdfService;
    private final UserViewService userViewService;

    public ChannelService(ChannelRepository channelRepository, TileInfoService tileInfoService, ObjectInfoService objectInfoService, MemoService memoService, PdfService pdfService, UserViewService userViewService) {
        this.channelRepository = channelRepository;
        this.tileInfoService = tileInfoService;
        this.objectInfoService = objectInfoService;
        this.memoService = memoService;
        this.pdfService = pdfService;
        this.userViewService = userViewService;
    }

    @Transactional
    public ChannelSingleResponseDTO registerChannelInitInfo(ChannelRegistRequestDTO channelRegistRequestDTO, String accountId) {
        Channel channel = channelRepository.save(new Channel(channelRegistRequestDTO.getChannelName()));

        User user = userViewService.findUserByEpicAccountId(accountId);
        user.getChannelList().add(channel.getChannelId());

        return new ChannelSingleResponseDTO(channel);
    }

    @Transactional
    public ChannelSingleResponseDTO updateChannelInitInfo(ChannelRegistRequestDTO channelRegistRequestDTO, String channelId) {
        Channel channel = channelRepository.findById(channelId).orElse(null);

        if(channel != null) {
            channel.setChannelName(channelRegistRequestDTO.getChannelName());
            return new ChannelSingleResponseDTO(channel);
        }
        else throw  new IllegalArgumentException("잘못된 channelId로 수정 요청중");
    }

    @Transactional
    public void deleteChannelByChannelId(String channelId) {

        tileInfoService.deleteTileInfosByChannelId(channelId);
        objectInfoService.deleteObjectInfosByChannelId(channelId);
        memoService.deleteMemosByChannelId(channelId);
        pdfService.deletePdfsByChannelId(channelId);
        channelRepository.deleteById(channelId);

    }
}
