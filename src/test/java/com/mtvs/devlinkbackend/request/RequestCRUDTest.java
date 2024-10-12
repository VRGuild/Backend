package com.mtvs.devlinkbackend.request;

import com.mtvs.devlinkbackend.request.dto.RequestRegistRequestDTO;
import com.mtvs.devlinkbackend.request.dto.RequestUpdateRequestDTO;
import com.mtvs.devlinkbackend.request.service.RequestService;
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

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class RequestCRUDTest {
    @Autowired
    private RequestService requestService;

    private static Stream<Arguments> newRequest() {
        return Stream.of(
                Arguments.of(new RequestRegistRequestDTO("의뢰0", "내용0", LocalDateTime.now(), LocalDateTime.now()), "계정0"),
                Arguments.of(new RequestRegistRequestDTO("의뢰00", "내용00", LocalDateTime.now(), LocalDateTime.now()), "계정00")
        );
    }

    private static Stream<Arguments> modifiedRequest() {
        return Stream.of(
                Arguments.of(new RequestUpdateRequestDTO(1L,"의뢰0", "내용0",  LocalDateTime.now(), LocalDateTime.now()), "계정1"),
                Arguments.of(new RequestUpdateRequestDTO(2L,"의뢰00" , "내용00",  LocalDateTime.now(), LocalDateTime.now()), "계정1")
        );
    }

    @DisplayName("의뢰 추가 테스트")
    @ParameterizedTest
    @MethodSource("newRequest")
    @Order(0)
    public void testCreateQuestion(RequestRegistRequestDTO requestRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> requestService.registRequest(requestRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 의뢰 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindRequestByRequestId(long questionId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestByRequestId(questionId)));
    }

    @DisplayName("계정 ID에 따른 의뢰 paging 조회 테스트")
    @ValueSource( strings = {"계정1", "계정2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestsByAccountId(accountId)));
    }

    @DisplayName("의뢰 수정 테스트")
    @MethodSource("modifiedRequest")
    @ParameterizedTest
    @Order(3)
    public void testUpdateRequest(RequestUpdateRequestDTO RequestUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(requestService.updateRequest(RequestUpdateRequestDTO, accountId)));
    }

    @DisplayName("의뢰 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(4)
    public void testDeleteRequest(long requestId) {
        Assertions.assertDoesNotThrow(() ->
                requestService.deleteRequest(requestId));
    }
}
