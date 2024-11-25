package com.mtvs.devlinkbackend.channel.repository;

import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends MongoRepository<MemoInfo, String> {

}
