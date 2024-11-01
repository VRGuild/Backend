package com.mtvs.devlinkbackend.user.command.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    void deleteByUser_EpicAccountId(String epicAccountId);
}
