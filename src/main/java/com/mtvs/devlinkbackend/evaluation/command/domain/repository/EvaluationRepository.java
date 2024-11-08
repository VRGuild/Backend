package com.mtvs.devlinkbackend.evaluation.command.domain.repository;

import com.mtvs.devlinkbackend.evaluation.command.domain.model.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
