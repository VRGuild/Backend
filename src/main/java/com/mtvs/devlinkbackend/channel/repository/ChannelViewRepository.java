package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ChannelViewRepository extends MongoRepository<Channel, String> {
    Page<Channel> findAll(Pageable pageable);
}
