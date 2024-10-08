package com.mtvs.devlinkbackend.question.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import com.mtvs.devlinkbackend.question.dto.QuestionRegistRequestDTO;
import com.mtvs.devlinkbackend.question.dto.QuestionUpdateRequestDTO;
import com.mtvs.devlinkbackend.question.entity.Question;
import com.mtvs.devlinkbackend.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final JwtUtil jwtUtil;

    @Autowired
    public QuestionController(QuestionService questionService, JwtUtil jwtUtil) {
        this.questionService = questionService;
        this.jwtUtil = jwtUtil;
    }

    // Create a new question
    @PostMapping
    public ResponseEntity<Question> createQuestion(
            @RequestBody QuestionRegistRequestDTO questionRegistRequestDTO,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromToken(accessToken);
        Question createdQuestion = questionService.registQuestion(questionRegistRequestDTO, accountId);
        return ResponseEntity.ok(createdQuestion);
    }

    // Retrieve a question by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable long questionId) {
        Question question = questionService.findQuestionByQuestionId(questionId);
        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Retrieve all questions with pagination
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestionsWithPaging(@RequestParam int page) {
        List<Question> questions = questionService.findAllQuestionsWithPaging(page);
        return ResponseEntity.ok(questions);
    }

    // Retrieve questions by account ID with pagination
    @GetMapping
    public ResponseEntity<List<Question>> getQuestionsByAccountIdWithPaging(
            @RequestParam int page,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromToken(accessToken);
        List<Question> questions = questionService.findQuestionsByAccountIdWithPaging(page, accountId);
        return ResponseEntity.ok(questions);
    }

    // Update a question by ID
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(
            @RequestBody QuestionUpdateRequestDTO questionUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromToken(accessToken);
        try {
            Question updatedQuestion = questionService.updateQuestion(questionUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a question by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
