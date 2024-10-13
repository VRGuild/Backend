package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.reply.dto.ReplyRegistRequestDTO;
import com.mtvs.devlinkbackend.reply.dto.ReplyUpdateRequestDTO;
import com.mtvs.devlinkbackend.reply.service.ReplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
public class ReplyCRUDTest {

    @Autowired
    private ReplyService replyService;

    private static Stream<Arguments> newReply() {
        return Stream.of(
                Arguments.of(new ReplyRegistRequestDTO("답변 내용 1-4", 1L), "계정0"),
                Arguments.of(new ReplyRegistRequestDTO("답변 내용 1-5", 1L), "계정00")
        );
    }

    private static Stream<Arguments> modifiedReply() {
        return Stream.of(
                Arguments.of(new ReplyUpdateRequestDTO(1L, "변경 답변 내용"), "계정1"),
                Arguments.of(new ReplyUpdateRequestDTO(2L,"변경 답변 내용" ), "계정1")
        );
    }

    @DisplayName("답변 추가 테스트")
    @MethodSource("newReply")
    @ParameterizedTest
    public void testCreateReply(ReplyRegistRequestDTO replyRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                replyService.registReply(replyRegistRequestDTO, accountId));
    }

    @DisplayName("답변 단일 조회 테스트")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindReply(Long replyId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(replyService.findReplyByReplyId(replyId)));
    }

    @DisplayName("계정 ID에 따른 답변 조회 테스트")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindReplyByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(replyService.findRepliesByAccountId(accountId)));
    }

    @DisplayName("질문 ID에 따른 답변 조회 테스트")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindReplyByQuestionId(Long questionId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(replyService.findRepliesByQuestionId(questionId)));
    }

    @DisplayName("답변 수정 테스트")
    @MethodSource("modifiedReply")
    @ParameterizedTest
    public void testUpdateReply(ReplyUpdateRequestDTO replyUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(replyService.updateReply(replyUpdateRequestDTO, accountId)));
    }

    @DisplayName("답변 삭제 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    public void testDeleteReply(Long replyId) {
        Assertions.assertDoesNotThrow(() ->
                replyService.deleteReply(replyId));
    }
}
