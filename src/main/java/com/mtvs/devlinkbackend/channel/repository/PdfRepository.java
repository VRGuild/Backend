package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends MongoRepository<PdfInfo, String> {
    void deleteAllByChannelId(String channelId);
}
