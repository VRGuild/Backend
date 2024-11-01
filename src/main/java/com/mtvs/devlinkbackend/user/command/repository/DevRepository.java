package com.mtvs.devlinkbackend.user.command.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevRepository extends JpaRepository<Dev, Long> {
    void deleteByUserId(Long userId);
}
