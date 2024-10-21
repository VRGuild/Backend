package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.request.dto.RequestRegistRequestDTO;
import com.mtvs.devlinkbackend.request.dto.RequestUpdateRequestDTO;
import com.mtvs.devlinkbackend.request.service.RequestService;
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

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class RequestCRUDTest {
    @Autowired
    private RequestService requestService;

    private static Stream<Arguments> newRequest() {
        return Stream.of(
                Arguments.of(new RequestRegistRequestDTO(
                        "업무범위0",
                        "근무형태0",
                        "진행분류0",
                        "회사이름0",
                        "의뢰0",
                        "내용0",
                        3,
                        3,
                        3,
                        3,
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.now()), "계정0"),
                Arguments.of(new RequestRegistRequestDTO(
                        "업무범위1",
                        "근무형태1",
                        "진행분류1",
                        "회사이름1",
                        "의뢰1",
                        "내용1",
                        2,
                        3,
                        1,
                        1,
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.now()), "계정00")
        );
    }

    private static Stream<Arguments> modifiedRequest() {
        return Stream.of(
                Arguments.of(new RequestUpdateRequestDTO(
                        1L,
                        "업무범위0",
                        "근무형태0",
                        "진행분류0",
                        "회사이름0",
                        "의뢰0",
                        "내용0",
                        2,
                        3,
                        1,
                        1,
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.now()), "계정1"),
                Arguments.of(new RequestUpdateRequestDTO(
                        2L,
                        "업무범위1",
                        "근무형태1",
                        "진행분류1",
                        "회사이름1",
                        "의뢰1",
                        "내용1",
                        2,
                        3,
                        1,
                        1,
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.now()), "계정1")
        );
    }

    @DisplayName("의뢰 추가 테스트")
    @ParameterizedTest
    @MethodSource("newRequest")
    @Order(0)
    public void testCreateRequest(RequestRegistRequestDTO requestRegistRequestDTO, String accountId) {
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

    @DisplayName("업무 범위에 따른 의뢰 조회 테스트")
    @ValueSource( strings = {"업무범위1", "업무범위2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByWorkScope(String workScope) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestsByWorkScope(workScope)));
    }

    @DisplayName("근무 형태에 따른 의뢰 조회 테스트")
    @ValueSource( strings = {"근무형태1", "근무형태2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByWorkType(String workType) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestsByWorkType(workType)));
    }

    @DisplayName("진행 분류에 따른 의뢰 조회 테스트")
    @ValueSource( strings = {"진행분류1", "진행분류2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByProgressClassification(String progressClassification) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.
                        findRequestsByProgressClassification(progressClassification)));
    }

    @DisplayName("프로젝트 주제(제목)에 따른 의뢰 조회 테스트")
    @ValueSource( strings = {"제목1", "제목2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByTitleContainingIgnoreCase(String title) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestsByTitleContainingIgnoreCase(title)));
    }

    @DisplayName("필요 직군보다 더 많이 모집하는 의뢰 조회 테스트")
    @CsvSource({"2,2,3,1,3", "3,2,1,1,3"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsWithLargerRequirements(Integer requiredClient, Integer requiredServer,
                                                       Integer requiredDesign, Integer requiredPlanner,
                                                       Integer requiredAIEngineer) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + requestService.findRequestsWithLargerRequirements(
                        requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer
                )));
    }
}
