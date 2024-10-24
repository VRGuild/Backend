package com.mtvs.devlinkbackend.support.repository;

import com.mtvs.devlinkbackend.support.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findSupportsByProjectId(Long projectId);
    List<Support> findSupportsByTeamId(Long teamId);
    @Query("SELECT s.teamId FROM Support s WHERE s.projectId = :projectId")
    List<Long> findTeamIdByProjectId(@Param("projectId") Long projectId);
}
