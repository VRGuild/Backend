package com.mtvs.devlinkbackend.user.query.service;

import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.repository.SkillCategoryInfoViewRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillCategoryViewService {
    private final SkillCategoryInfoViewRepository skillCategoryInfoViewRepository;

    public SkillCategoryViewService(SkillCategoryInfoViewRepository skillCategoryInfoViewRepository) {
        this.skillCategoryInfoViewRepository = skillCategoryInfoViewRepository;
    }

    public SkillCategoryInfo findById(Long skillCategoryInfoId) {
        return skillCategoryInfoViewRepository.findById(skillCategoryInfoId).orElse(null);
    }
}
