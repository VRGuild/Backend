package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.ObjectInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.ObjectInfo;
import com.mtvs.devlinkbackend.channel.entity.Position;
import com.mtvs.devlinkbackend.channel.repository.ObjectInfoRepository;
import com.mtvs.devlinkbackend.channel.repository.ObjectInfoViewRepository;
import com.mtvs.devlinkbackend.channel.service.ObjectInfoService;
import com.mtvs.devlinkbackend.channel.service.ObjectInfoViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ObjectInfoCRUDTest {

    @Autowired
    private ObjectInfoService objectInfoService;

    @Autowired
    private ObjectInfoRepository objectInfoRepository;
    @Autowired
    private ObjectInfoViewRepository objectInfoViewRepository;
    @Autowired
    private ObjectInfoViewService objectInfoViewService;

    @Test
    void testInsertObjectInfoByChannelId() {
        // Given
        String channelId = "channel123";
        ObjectInfoRegistDTO requestDTO = new ObjectInfoRegistDTO(
                "TestObject", "TestClass",
                new Position(10, 20, 30),
                new Position(0, 90, 0)
        );

        // When
        ObjectInfoSingleResponseDTO objectInfoDTO = objectInfoService.insertObjectInfoByChannelId(requestDTO, channelId);

        // Then
        ObjectInfo objectInfo = objectInfoViewRepository.findAllByChannelId(channelId).get(0);

        assertThat(objectInfo.getChannelId()).isEqualTo(channelId);
        assertThat(objectInfo.getObjectName()).isEqualTo("TestObject");
        assertThat(objectInfo.getObjectClassName()).isEqualTo("TestClass");
        assertThat(objectInfo.getPosition().getX()).isEqualTo(10);
    }

    @Test
    void testFindObjectInfoListByChannelId() {
        // Given
        String channelId = "channel123";
        ObjectInfo objectInfo1 = objectInfoRepository.save(new ObjectInfo(
                "Object1", "Class1",
                new Position(10, 20, 30),
                new Position(0, 0, 0),
                channelId
        ));
        ObjectInfo objectInfo2 = objectInfoRepository.save(new ObjectInfo(
                "Object2", "Class2",
                new Position(40, 50, 60),
                new Position(90, 0, 90),
                channelId
        ));

        // When
        ObjectInfoListResponseDTO responseDTO = objectInfoViewService.findObjectInfoListByChannelId(channelId);

        // Then
        List<ObjectInfo> objectInfoList = responseDTO.getData();
        assertThat(objectInfoList).hasSize(2);
        assertThat(objectInfoList.get(0).getObjectName()).isEqualTo("Object1");
        assertThat(objectInfoList.get(1).getObjectClassName()).isEqualTo("Class2");
    }

    @Test
    void testUpdateObjectInfoByObjectInfoId() {
        // Given
        String channelId = "channel456";
        ObjectInfo existingObject = objectInfoService.insertObjectInfoByChannelId(new ObjectInfoRegistDTO(
                "OldObject", "OldClass",
                new Position(15, 25, 35),
                new Position(0, 0, 0)
        ), channelId).getData();

        ObjectInfoRegistDTO updateDTO = new ObjectInfoRegistDTO(
                "UpdatedObject", "UpdatedClass",
                new Position(20, 30, 40),
                new Position(45, 45, 45)
        );

        // When
        ObjectInfoSingleResponseDTO responseDTO = objectInfoService.updateObjectInfoByObjectId(updateDTO, existingObject.getObjectId());

        // Then
        assertThat(responseDTO.getData().getObjectName()).isEqualTo("UpdatedObject");
        assertThat(responseDTO.getData().getPosition().getX()).isEqualTo(20);
    }

    @Test
    void testDeleteObjectInfoByObjectInfoId() {
        // Given
        String channelId = "channel789";
        ObjectInfo objectInfo = objectInfoRepository.save(new ObjectInfo(
                "TestObject", "TestClass",
                new Position(10, 20, 30),
                new Position(0, 90, 0),
                channelId
        ));

        // When
        objectInfoService.deleteObjectInfoByObjectId(objectInfo.getObjectId());

        // Then
        boolean exists = objectInfoRepository.existsById(objectInfo.getObjectId());
        assertThat(exists).isFalse();
    }

    @Test
    void testUpdateObjectInfoWithInvalidId() {
        // Given
        String invalidObjectId = "invalid123";
        ObjectInfoRegistDTO updateDTO = new ObjectInfoRegistDTO(
                "UpdatedObject", "UpdatedClass",
                new Position(20, 30, 40),
                new Position(45, 45, 45)
        );

        // When/Then
        assertThatThrownBy(() -> objectInfoService.updateObjectInfoByObjectId(updateDTO, invalidObjectId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 ObjectInfoId로 수정 요청");
    }
}
