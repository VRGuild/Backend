package com.mtvs.devlinkbackend.question.repository;

import com.mtvs.devlinkbackend.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findQuestionsByAccountId(String accountId, Pageable pageable);
}
