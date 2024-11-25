package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TileInfoRepository extends MongoRepository<TileInfo, String> {
    void deleteAllByChannelId(String channelId);
}
