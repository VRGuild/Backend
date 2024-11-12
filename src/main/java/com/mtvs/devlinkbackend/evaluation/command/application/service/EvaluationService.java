package com.mtvs.devlinkbackend.evaluation.command.application.service;

import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationRegistRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationUpdateRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.response.EvaluationSingleResponseDTO;
import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import com.mtvs.devlinkbackend.evaluation.command.domain.repository.EvaluationRepository;
import com.mtvs.devlinkbackend.evaluation.query.service.EvaluationViewService;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.service.SkillCategoryViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final SkillCategoryViewService skillCategoryViewService;
    private final EvaluationViewService evaluationViewService;

    public EvaluationService(EvaluationRepository evaluationRepository, SkillCategoryViewService skillCategoryViewService, EvaluationViewService evaluationViewService) {
        this.evaluationRepository = evaluationRepository;
        this.skillCategoryViewService = skillCategoryViewService;
        this.evaluationViewService = evaluationViewService;
    }

    @Transactional
    public EvaluationSingleResponseDTO registerEvaluation(EvaluationRegistRequestDTO evaluationRegistRequestDTO, Long estimatorId) {
        SkillCategoryInfo skillCategoryInfo =
                skillCategoryViewService.findById(evaluationRegistRequestDTO.getCategoryId());

        if(skillCategoryInfo == null)
            throw new IllegalArgumentException("잘못된 skillCategoryInfoId로 평가 작성 시도중");

        Evaluation savedEvaluation = evaluationRepository.save(new Evaluation(
                estimatorId,
                evaluationRegistRequestDTO.getEvaluationInfo().getEstimatederId(),
                evaluationRegistRequestDTO.getEvaluationInfo().getCause(),
                evaluationRegistRequestDTO.getEvaluationInfo().getPoint(),
                skillCategoryInfo
        ));

        skillCategoryInfo.getEvaluationIdList().add(savedEvaluation.getEvaluationId());

        if (skillCategoryInfo.getEvaluationIdList().size() == 5)
            skillCategoryInfo.setIsEvaluated(true);

        if (skillCategoryInfo.getIsEvaluated()) {
            // 기존의 PointAvg를 활용한 계산은 소수점 2번째 자리 이후부터 누락되는 값이 존재 가능
            // 따라서 전부 불러와 계산하는 방식을 사용
            double evaluationPointAvg =
                    evaluationViewService.findEvaluationsByEvaluationIdList(skillCategoryInfo.getEvaluationIdList())
                            .stream().mapToInt(Evaluation::getPoint).average().orElse(0.00);

            skillCategoryInfo.setPointAvg(formatToTwoDecimalPlaces(evaluationPointAvg));
        }

        return new EvaluationSingleResponseDTO(savedEvaluation);
    }

    @Transactional
    public EvaluationSingleResponseDTO updateEvaluation(EvaluationUpdateRequestDTO evaluationUpdateRequestDTO) {
        Evaluation evaluation =
                evaluationRepository.findById(evaluationUpdateRequestDTO.getEvaluationId()).orElse(null);

        if (evaluation == null)
            throw new IllegalArgumentException("잘못된 evaluationId로 호출중");

        if (!evaluation.getEstimatorId().equals(evaluationUpdateRequestDTO.getEstimatorId()))
            throw new IllegalArgumentException("자신이 평가하지 않은 평가 내용을 수정중 - userId : "
                    + evaluationUpdateRequestDTO.getEstimatorId());

        evaluation.setCause(evaluationUpdateRequestDTO.getCause());
        evaluation.setPoint(evaluationUpdateRequestDTO.getPoint());

        return new EvaluationSingleResponseDTO(evaluation);
    }

    public void deleteById(Long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    private double formatToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
    }
}
