package com.mtvs.devlinkbackend.question.service;

import com.mtvs.devlinkbackend.question.dto.response.QuestionPagingResponseDTO;
import com.mtvs.devlinkbackend.question.dto.request.QuestionRegistRequestDTO;
import com.mtvs.devlinkbackend.question.dto.request.QuestionUpdateRequestDTO;
import com.mtvs.devlinkbackend.question.entity.Question;
import com.mtvs.devlinkbackend.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    private static final int pageSize = 15;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Question registQuestion(QuestionRegistRequestDTO questionRegistRequestDTO, String accountId) {
        return questionRepository.save(
                new Question(
                        questionRegistRequestDTO.getTitle(),
                        questionRegistRequestDTO.getContent(),
                        accountId
                )
        );
    }

    public Question findQuestionByQuestionId(long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    // findAll with pagination
    public QuestionPagingResponseDTO findAllQuestionsWithPaging(int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Question> questionPage = questionRepository.findAll(pageable);
        return new QuestionPagingResponseDTO(questionPage.getContent(), questionPage.getTotalPages()); // Returns the list of questions
    }

    // findQuestionsByAccountId with pagination
    public QuestionPagingResponseDTO findQuestionsByAccountIdWithPaging(int page, String accountId) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Question> questionPage = questionRepository.findQuestionsByAccountId(accountId, pageable);
        return new QuestionPagingResponseDTO(questionPage.getContent(), questionPage.getTotalPages()); // Returns the list of questions for the given accountId
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Question updateQuestion(QuestionUpdateRequestDTO questionUpdateRequestDTO, String accountId) {
        Optional<Question> foundQuestion =
                questionRepository.findById(questionUpdateRequestDTO.getQuestionId());
        if (foundQuestion.isPresent()) {
            if (foundQuestion.get().getAccountId().equals(accountId)) {
                Question question = foundQuestion.get();
                question.setTitle(questionUpdateRequestDTO.getTitle());
                question.setContent(questionUpdateRequestDTO.getContent());
                return question;
            } else throw new IllegalArgumentException("Question Update Error : 다른 사용자가 남의 질문 내용 변경 시도");
        }
        else throw new IllegalArgumentException(
                "Question ID : "+ questionUpdateRequestDTO.getQuestionId() +" not found");
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
        System.out.println("질문ID = " + questionId + " ,삭제 시간 : " + LocalDateTime.now());
    }
}
