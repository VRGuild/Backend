package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationRegistRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.request.EvaluationUpdateRequestDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.dto.response.EvaluationSingleResponseDTO;
import com.mtvs.devlinkbackend.evaluation.command.application.service.EvaluationService;
import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import com.mtvs.devlinkbackend.evaluation.command.domain.repository.EvaluationRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.query.repository.SkillCategoryInfoViewRepository;
import com.mtvs.devlinkbackend.user.query.service.EpicDevViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class EvaluationCRUDTest {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private SkillCategoryInfoViewRepository skillCategoryInfoViewRepository;

    @Autowired
    private EvaluationService evaluationService;

    private SkillCategoryInfo skillCategoryInfo;
    @Autowired
    private EpicDevViewService epicDevViewService;

    @BeforeEach
    void setUp() {
        Dev dev = epicDevViewService.findAllDevsWithPagination(0).getData().get(0);

        skillCategoryInfo = new SkillCategoryInfo();
        skillCategoryInfo.setEvaluationIdList(new ArrayList<>());
        skillCategoryInfo.setCategoryName("Test Category");
        skillCategoryInfo.setIsEvaluated(false);
        skillCategoryInfo.setDev(dev);

        // 저장된 SkillCategoryInfo 객체를 사용하기 위해 설정
        skillCategoryInfoViewRepository.save(skillCategoryInfo);
    }

    @Test
    void registerEvaluation_shouldSaveEvaluation() {
        // Given
        EvaluationRegistRequestDTO request = new EvaluationRegistRequestDTO(
                skillCategoryInfo.getCategoryInfoId(),
                new Evaluation(1L,2L, "Good Job", 80, skillCategoryInfo)
        );

        // When
        EvaluationSingleResponseDTO response = evaluationService.registerEvaluation(request,2L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getEvaluationId()).isNotNull();
        assertThat(skillCategoryInfo.getEvaluationIdList()).contains(response.getData().getEvaluationId());
    }

    @Test
    void updateEvaluation_shouldUpdateEvaluation() {
        // Given
        Evaluation existingEvaluation = new Evaluation(1L,2L, "Initial Cause", 70, skillCategoryInfo);
        evaluationRepository.save(existingEvaluation);

        EvaluationUpdateRequestDTO updateRequest = new EvaluationUpdateRequestDTO(
                existingEvaluation.getEvaluationId(),
                existingEvaluation.getEstimatorId(),
                existingEvaluation.getEstimatederId(),
                "Updated Cause",
                85
        );

        // When
        EvaluationSingleResponseDTO response = evaluationService.updateEvaluation(updateRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getCause()).isEqualTo("Updated Cause");
        assertThat(response.getData().getPoint()).isEqualTo(85);
    }

    @Test
    void deleteById_shouldDeleteEvaluation() {
        // Given
        Evaluation evaluation = new Evaluation(1L, 2L,"To be deleted", 70, skillCategoryInfo);
        evaluationRepository.save(evaluation);

        // When
        evaluationService.deleteById(evaluation.getEvaluationId());

        // Then
        assertThat(evaluationRepository.findById(evaluation.getEvaluationId())).isEmpty();
    }
}
