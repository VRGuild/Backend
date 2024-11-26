package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.ObjectInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectInfoRepository extends MongoRepository<ObjectInfo, String> {
    void deleteAllByChannelId(String channelId);
}
