package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.request.ObjectInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.ObjectInfo;
import com.mtvs.devlinkbackend.channel.repository.ObjectInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObjectInfoService {

    private final ObjectInfoRepository objectInfoRepository;

    public ObjectInfoService(ObjectInfoRepository objectInfoRepository) {
        this.objectInfoRepository = objectInfoRepository;
    }

    @Transactional
    public ObjectInfoSingleResponseDTO insertObjectInfoByChannelId(ObjectInfoRegistDTO objectInfoRegistDTO, String channelId) {
        ObjectInfo objectInfo = objectInfoRepository.save(new ObjectInfo(
                objectInfoRegistDTO.getObjectName(),
                objectInfoRegistDTO.getObjectClassName(),
                objectInfoRegistDTO.getPosition(),
                objectInfoRegistDTO.getRotator(),
                channelId
        ));

        return new ObjectInfoSingleResponseDTO(objectInfo);
    }

    @Transactional
    public ObjectInfoSingleResponseDTO updateObjectInfoByObjectId(
            ObjectInfoRegistDTO objectInfoRegistDTO, String objectId, String channelId) {

        ObjectInfo objectInfo = objectInfoRepository.findById(objectId).orElse(null);

        if (objectInfo != null) {
            objectInfo.setObjectName(objectInfoRegistDTO.getObjectName());
            objectInfo.setObjectClassName(objectInfoRegistDTO.getObjectClassName());
            objectInfo.setPosition(objectInfoRegistDTO.getPosition());
            objectInfo.setRotator(objectInfoRegistDTO.getRotator());
            objectInfo.setChannelId(channelId);

            return new ObjectInfoSingleResponseDTO(objectInfo);
        }
        else throw new IllegalArgumentException("잘못된 ObjectInfoId로 수정 요청");
    }

    @Transactional
    public void deleteObjectInfoByObjectId(String objectId) {
        objectInfoRepository.deleteById(objectId);
    }
}
