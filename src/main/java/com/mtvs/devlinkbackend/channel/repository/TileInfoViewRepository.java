package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TileInfoViewRepository extends MongoRepository<TileInfo, String> {
    List<TileInfo> findAllByChannelId(String channelId);
}
