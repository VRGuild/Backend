package com.mtvs.devlinkbackend.comment;

import com.mtvs.devlinkbackend.comment.dto.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class CommentCRUDTest {
    @Autowired
    private CommentService commentService;

    private static Stream<Arguments> newComment() {
        return Stream.of(
                Arguments.of(new CommentRegistRequestDTO("내용0", 1L), "계정0"),
                Arguments.of(new CommentRegistRequestDTO("내용00", 2L), "계정00")
        );
    }

    private static Stream<Arguments> modifiedComment() {
        return Stream.of(
                Arguments.of(new CommentUpdateRequestDTO(1L, "내용0"), "계정1"),
                Arguments.of(new CommentUpdateRequestDTO(2L, "내용00"), "계정2")
        );
    }

    @DisplayName("코멘트 추가 테스트")
    @ParameterizedTest
    @MethodSource("newComment")
    @Order(0)
    public void testCreateComment(CommentRegistRequestDTO commentRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> commentService.registComment(commentRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 코멘트 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindCommentByCommentId(long commentId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Comment = " + commentService.findCommentByCommentId(commentId)));
    }

    @DisplayName("계정 ID에 따른 코멘트 조회 테스트")
    @ValueSource( strings = {"계정1", "계정2"})
    @ParameterizedTest
    @Order(2)
    public void testFindCommentsByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Comment = " + commentService.findCommentsByAccountId(accountId)));
    }

    @DisplayName("의뢰 ID에 따른 코멘트 조회 테스트")
    @ValueSource( longs = {1,2})
    @ParameterizedTest
    @Order(3)
    public void testFindCommentsByRequestId(long requestId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Comment = " + commentService.findCommentsByRequestId(requestId)));
    }

    @DisplayName("코멘트 수정 테스트")
    @MethodSource("modifiedComment")
    @ParameterizedTest
    @Order(4)
    public void testUpdateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(commentService.updateComment(commentUpdateRequestDTO, accountId)));
    }

    @DisplayName("코멘트 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(5)
    public void testDeleteRequest(long commentId) {
        Assertions.assertDoesNotThrow(() ->
                commentService.deleteComment(commentId));
    }
}
