package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.ObjectInfo;
import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ObjectInfoViewRepository extends MongoRepository<ObjectInfo, String> {
    List<ObjectInfo> findAllByChannelId(String channelId);
}
