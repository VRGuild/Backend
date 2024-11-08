package com.mtvs.devlinkbackend.evaluation.query.service;

import com.mtvs.devlinkbackend.evaluation.command.application.dto.response.EvaluationListReponseDTO;
import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import com.mtvs.devlinkbackend.evaluation.query.repository.EvaluationViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.service.EpicDevViewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationViewService {
    private final EvaluationViewRepository evaluationViewRepository;
    private final EpicDevViewService epicDevViewService;

    public EvaluationViewService(EvaluationViewRepository evaluationViewRepository, EpicDevViewService epicDevViewService) {
        this.evaluationViewRepository = evaluationViewRepository;
        this.epicDevViewService = epicDevViewService;
    }

    public List<Evaluation> findEvaluationsByEvaluationIdList(List<Long> evaluationIdList) {
        return evaluationViewRepository.findAllById(evaluationIdList);
    }

    public EvaluationListReponseDTO findEvaluationListByUserIdAndCategoryName(
            Long userId, String categoryName) {

        Dev dev = epicDevViewService.findDevByUserId(userId).getData();
        if(dev == null)
            throw new IllegalArgumentException("잘못된 User ID로 SkillCategoryInfoDetail 조회중");

        SkillCategoryInfo skillCategoryInfo = dev.getSkillCategoryList().stream()
                .filter(categoryInfo -> categoryInfo.getCategoryName().equals(categoryName)).findFirst().orElse(null);

        if(skillCategoryInfo == null)
            throw new IllegalArgumentException("잘못된 CategoryName으로 SkillCategoryInfoDetail 조회 시도중");

        return new EvaluationListReponseDTO(findEvaluationsByEvaluationIdList(skillCategoryInfo.getEvaluationIdList()));
    }
}
