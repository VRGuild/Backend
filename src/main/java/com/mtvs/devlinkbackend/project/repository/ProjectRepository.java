package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Support s SET s.supportConfirmation = :confirmationStatus " +
            "WHERE s.projectId = :projectId AND s.teamId = :teamId " +
            "AND EXISTS (SELECT 1 FROM Project p WHERE p.projectId = :projectId AND p.userId = :userId)")
    void updateSupportConfirmation(@Param("projectId") Long projectId,
                                   @Param("teamId") Long teamId,
                                   @Param("userId") Long userId,
                                   @Param("confirmationStatus") AcceptStatus confirmationStatus);
}
