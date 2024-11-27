package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.repository.projection.SkillCategory_CategoryNameAndPointAvg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillCategoryInfoViewRepository extends JpaRepository<SkillCategoryInfo, Long> {
    List<SkillCategory_CategoryNameAndPointAvg> findByDev_DevId(Long devId);
    @Query("SELECT s FROM SkillCategoryInfo s WHERE s.dev.devId =:devId")
    List<SkillCategoryInfo> findByDevId(@Param("devId") Long devId);
}
