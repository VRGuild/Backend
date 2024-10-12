package com.mtvs.devlinkbackend.question.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.question.dto.QuestionRegistRequestDTO;
import com.mtvs.devlinkbackend.question.dto.QuestionUpdateRequestDTO;
import com.mtvs.devlinkbackend.question.entity.Question;
import com.mtvs.devlinkbackend.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "공개 질문 생성",
            description = "사용자가 올린 공개 질문을 생성한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "질문 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<Question> createQuestion(
            @RequestBody QuestionRegistRequestDTO questionRegistRequestDTO,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithAuth(accessToken);
        Question createdQuestion = questionService.registQuestion(questionRegistRequestDTO, accountId);
        return ResponseEntity.ok(createdQuestion);
    }

    // Retrieve a question by ID
    @GetMapping("/{questionId}")
    @Operation(
            summary = "PK에 따른 질문 조회",
            description = "PK값으로 사용자가 올린 공개 질문 1개를 조회한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.findQuestionByQuestionId(questionId);
        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Retrieve all questions with pagination
    @GetMapping("/all")
    @Operation(
            summary = "Pagination으로 전체 질문 조회",
            description = "Pagination으로 공개 질문 전체를 조회한다. 최대 20개가 주어진다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<List<Question>> getAllQuestionsWithPaging(@RequestParam int page) {
        List<Question> questions = questionService.findAllQuestionsWithPaging(page);
        return ResponseEntity.ok(questions);
    }

    // Retrieve questions by account ID with pagination
    @GetMapping
    @Operation(
            summary = "Pagination으로 로그인한 사용자의 질문 조회",
            description = "Pagination으로 사용자가 했던 질문 전체를 조회한다. 최대 20개가 주어진다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<List<Question>> getQuestionsByAccountIdWithPaging(
            @RequestParam int page,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(accessToken);
        List<Question> questions = questionService.findQuestionsByAccountIdWithPaging(page, accountId);
        return ResponseEntity.ok(questions);
    }

    // Update a question by ID
    @PatchMapping("/{id}")
    @Operation(
            summary = "사용자 질문 수정",
            description = "사용자가 했던 질문을 수정한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<Question> updateQuestion(
            @RequestBody QuestionUpdateRequestDTO questionUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String accessToken) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(accessToken);
        try {
            Question updatedQuestion = questionService.updateQuestion(questionUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a question by ID
    @DeleteMapping("/{questionId}")
    @Operation(
            summary = "사용자 질문 삭제",
            description = "사용자가 했던 질문을 삭제한다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {

        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
