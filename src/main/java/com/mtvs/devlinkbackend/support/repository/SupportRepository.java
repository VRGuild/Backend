package com.mtvs.devlinkbackend.support.repository;

import com.mtvs.devlinkbackend.support.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findSupportsByProjectId(Long projectId);
    List<Support> findSupportsByTeamId(Long teamId);
}
