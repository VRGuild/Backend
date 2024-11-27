package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.ObjectInfo;
import com.mtvs.devlinkbackend.channel.repository.ObjectInfoViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectInfoViewService {

    private final ObjectInfoViewRepository objectInfoViewRepository;

    public ObjectInfoViewService(ObjectInfoViewRepository objectInfoViewRepository) {
        this.objectInfoViewRepository = objectInfoViewRepository;
    }

    public ObjectInfoListResponseDTO findObjectInfoListByChannelId(String channelId) {
        List<ObjectInfo> objectInfoList = objectInfoViewRepository.findAllByChannelId(channelId);

        return new ObjectInfoListResponseDTO(objectInfoList);
    }
}
