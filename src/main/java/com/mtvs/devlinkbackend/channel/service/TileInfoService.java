package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.request.TileInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import com.mtvs.devlinkbackend.channel.repository.TileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TileInfoService {


    private final TileInfoRepository tileInfoRepository;

    public TileInfoService(TileInfoRepository tileInfoRepository) {
        this.tileInfoRepository = tileInfoRepository;
    }

    @Transactional
    public TileInfoRegistDTO insertTileInfoByChannelId(TileInfoRegistDTO tileInfoRegistDTO, String channelId) {
        TileInfo tileInfo = tileInfoRepository.save(new TileInfo(
                channelId,
                tileInfoRegistDTO.getPosition()
        ));

        return tileInfoRegistDTO;
    }

    @Transactional
    public TileInfoRegistDTO updateTileInfoByChannelId(TileInfoRegistDTO tileInfoRegistDTO, String channelId, String tileId) {
        TileInfo tileInfo = tileInfoRepository.findById(tileId).orElse(null);

        if(tileInfo != null) {
            tileInfo.setPosition(tileInfoRegistDTO.getPosition());
            return tileInfoRegistDTO;
        }
        else throw new IllegalArgumentException("잘못된 channelId로의 조회");
    }

    @Transactional
    public void deleteTileInfoByChannelId(String channelId, String tileId) {
        tileInfoRepository.deleteById(tileId);
    }
}
