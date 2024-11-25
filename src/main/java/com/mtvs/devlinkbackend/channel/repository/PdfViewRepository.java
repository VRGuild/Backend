package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfViewRepository extends MongoRepository<PdfInfo, String> {
    List<PdfInfo> findAllByChannelId(String channelId);
}
