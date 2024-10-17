package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {

}