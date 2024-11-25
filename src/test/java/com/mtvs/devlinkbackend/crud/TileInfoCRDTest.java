package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.TileInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.TileInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Position;
import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import com.mtvs.devlinkbackend.channel.repository.TileInfoRepository;
import com.mtvs.devlinkbackend.channel.repository.TileInfoViewRepository;
import com.mtvs.devlinkbackend.channel.service.TileInfoService;
import com.mtvs.devlinkbackend.channel.service.TileInfoViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TileInfoServiceTest {

    @Autowired
    private TileInfoService tileInfoService;

    @Autowired
    private TileInfoRepository tileInfoRepository;
    @Autowired
    private TileInfoViewService tileInfoViewService;
    @Autowired
    private TileInfoViewRepository tileInfoViewRepository;

    @Test
    void testInsertTileInfoByChannelId() {
        // Given
        String channelId = "channel123";
        TileInfoRegistDTO requestDTO = new TileInfoRegistDTO(new Position(10, 20, 30));

        // When
        tileInfoService.insertTileInfoByChannelId(requestDTO, channelId);

        // Then
        Optional<TileInfo> tileInfoOptional = tileInfoViewRepository.findAllByChannelId(channelId).stream().findFirst();
        assertThat(tileInfoOptional).isPresent();
        TileInfo tileInfo = tileInfoOptional.get();
        assertThat(tileInfo.getChannelId()).isEqualTo(channelId);
        assertThat(tileInfo.getPosition().getX()).isEqualTo(10);
        assertThat(tileInfo.getPosition().getY()).isEqualTo(20);
        assertThat(tileInfo.getPosition().getZ()).isEqualTo(30);
    }

    @Test
    void testFindTileInfoListByChannelId() {
        // Given
        String channelId = "channel789";
        tileInfoRepository.save(new TileInfo(channelId, new Position(10, 20, 30)));
        tileInfoRepository.save(new TileInfo(channelId, new Position(40, 50, 60)));

        // When
        TileInfoListResponseDTO responseDTO = tileInfoViewService.findTileInfoListByChannelId(channelId);

        // Then
        List<TileInfo> tileInfoList = responseDTO.getData();
        assertThat(tileInfoList).hasSize(2);
        assertThat(tileInfoList.get(0).getChannelId()).isEqualTo(channelId);
        assertThat(tileInfoList.get(1).getPosition().getX()).isEqualTo(40);
    }

    @Test
    void testDeleteTileInfoByChannelId() {
        // Given
        String channelId = "channel456";
        TileInfo tileInfo = tileInfoRepository.save(new TileInfo(channelId, new Position(15, 25, 35)));

        // When
        tileInfoService.deleteTileInfoByChannelId(channelId);

        // Then
        boolean exists = tileInfoViewRepository.findAllByChannelId(channelId).isEmpty();
        assertThat(exists).isTrue();
    }
}
