package com.mtvs.devlinkbackend.question;

import com.mtvs.devlinkbackend.question.dto.QuestionRegistRequestDTO;
import com.mtvs.devlinkbackend.question.dto.QuestionUpdateRequestDTO;
import com.mtvs.devlinkbackend.question.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class QuestionCRUDTest {

    @Autowired
    private QuestionService questionService;

    private static Stream<Arguments> newQuestion() {
        return Stream.of(
                Arguments.of(new QuestionRegistRequestDTO("질문0", "내용0"), "계정0"),
                Arguments.of(new QuestionRegistRequestDTO("질문00", "내용00"), "계정00")
        );
    }

    private static Stream<Arguments> modifiedQuestion() {
        return Stream.of(
                Arguments.of(new QuestionUpdateRequestDTO(1L,"질문0", "내용0"), "계정1"),
                Arguments.of(new QuestionUpdateRequestDTO(2L,"질문00" , "내용00"), "계정1")
        );
    }

    @DisplayName("질문 추가 테스트")
    @ParameterizedTest
    @MethodSource("newQuestion")
    @Order(0)
    public void testCreateQuestion(QuestionRegistRequestDTO questionRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> questionService.registQuestion(questionRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 질문 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindQuestionByQuestionId(long questionId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Question = " + questionService.findQuestionByQuestionId(questionId)));
    }

    @DisplayName("질문 paging 조회 테스트")
    @ValueSource(ints = {0,1})
    @ParameterizedTest
    @Order(2)
    public void testFindQuestionsWithPaging(int page) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Question = " + questionService.findAllQuestionsWithPaging(page)));
    }

    @DisplayName("계정 ID에 따른 질문 paging 조회 테스트")
    @CsvSource({"0,계정1", "0,계정2"})
    @ParameterizedTest
    @Order(3)
    public void testFindQuestionsByAccountIdWithPaging(int page, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Question = " + questionService.findQuestionsByAccountIdWithPaging(page, accountId)));
    }

    @DisplayName("질문 수정 테스트")
    @MethodSource("modifiedQuestion")
    @ParameterizedTest
    @Order(4)
    public void testUpdateQuestion(QuestionUpdateRequestDTO questionUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(questionService.updateQuestion(questionUpdateRequestDTO, accountId)));
    }

    @DisplayName("질문 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(5)
    public void testDeleteQuestion(long questionId) {
        Assertions.assertDoesNotThrow(() ->
                questionService.deleteQuestion(questionId));
    }
}
