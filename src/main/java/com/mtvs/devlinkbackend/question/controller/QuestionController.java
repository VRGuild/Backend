package com.mtvs.devlinkbackend.question.controller;

import com.mtvs.devlinkbackend.question.dto.response.QuestionPagingResponseDTO;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.question.dto.request.QuestionRegistRequestDTO;
import com.mtvs.devlinkbackend.question.dto.request.QuestionUpdateRequestDTO;
import com.mtvs.devlinkbackend.question.entity.Question;
import com.mtvs.devlinkbackend.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithAuth(authorizationHeader);
        Question createdQuestion = questionService.registQuestion(questionRegistRequestDTO, accountId);
        return ResponseEntity.ok(createdQuestion);
    }

    // Retrieve a question by ID
    @Operation(
            summary = "PK에 따른 질문 조회",
            description = "PK값으로 사용자가 올린 공개 질문 1개를 조회한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/{questionId}")
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
    public ResponseEntity<QuestionPagingResponseDTO> getAllQuestionsWithPaging(@RequestParam int page) {
        QuestionPagingResponseDTO questionPagingResponseDTO = questionService.findAllQuestionsWithPaging(page);
        return ResponseEntity.ok(questionPagingResponseDTO);
    }

    // Retrieve questions by account ID with pagination
    @Operation(
            summary = "Pagination으로 로그인한 사용자의 질문 조회",
            description = "Pagination으로 사용자가 했던 질문 전체를 조회한다. 최대 20개가 주어진다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/account")
    public ResponseEntity<QuestionPagingResponseDTO> getQuestionsByAccountIdWithPaging(
            @RequestParam int page,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
    QuestionPagingResponseDTO questionPagingResponseDTO = questionService.findQuestionsByAccountIdWithPaging(page, accountId);
        return ResponseEntity.ok(questionPagingResponseDTO);
    }

    // Update a question by ID
    @Operation(
            summary = "사용자 질문 수정",
            description = "사용자가 했던 질문을 수정한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PatchMapping
    public ResponseEntity<Question> updateQuestion(
            @RequestBody QuestionUpdateRequestDTO questionUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        try {
            Question updatedQuestion = questionService.updateQuestion(questionUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a question by ID
    @Operation(
            summary = "사용자 질문 삭제",
            description = "사용자가 했던 질문을 삭제한다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {

        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
