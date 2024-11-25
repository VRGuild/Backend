package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoViewRepository extends MongoRepository<MemoInfo,String> {
    List<MemoInfo> findAllByChannelId(String channelId);
}
