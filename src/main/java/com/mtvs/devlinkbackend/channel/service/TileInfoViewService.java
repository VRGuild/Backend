package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.TileInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import com.mtvs.devlinkbackend.channel.repository.TileInfoViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TileInfoViewService {

    private final TileInfoViewRepository tileInfoViewRepository;

    public TileInfoViewService(TileInfoViewRepository tileInfoViewRepository) {
        this.tileInfoViewRepository = tileInfoViewRepository;
    }

    public TileInfoListResponseDTO findTileInfoListByChannelId(String channelId) {
        List<TileInfo> tileInfoList = tileInfoViewRepository.findAllByChannelId(channelId);
        return new TileInfoListResponseDTO(tileInfoList);
    }
}
