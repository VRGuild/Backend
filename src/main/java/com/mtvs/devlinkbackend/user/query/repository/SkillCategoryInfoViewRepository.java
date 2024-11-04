package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.repository.projection.SkillCategory_CategoryNameAndPointAvg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillCategoryInfoViewRepository extends JpaRepository<SkillCategoryInfo, Long> {
    List<SkillCategory_CategoryNameAndPointAvg> findByDev_DevId(Long devId);
}
