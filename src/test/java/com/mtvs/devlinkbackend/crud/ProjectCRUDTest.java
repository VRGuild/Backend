package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.project.dto.request.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.service.ProjectService;
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
public class ProjectCRUDTest {
    @Autowired
    private ProjectService projectService;

    private static Stream<Arguments> newRequest() {
        return Stream.of(
                Arguments.of(new ProjectRegistRequestDTO(
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
                        LocalDateTime.now(),
                        1000000), "계정0"),
                Arguments.of(new ProjectRegistRequestDTO(
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
                        LocalDateTime.now(),
                        1000000), "계정00")
        );
    }

    private static Stream<Arguments> modifiedRequest() {
        return Stream.of(
                Arguments.of(new ProjectUpdateRequestDTO(
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
                        LocalDateTime.now(),
                        1000000), "계정1"),
                Arguments.of(new ProjectUpdateRequestDTO(
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
                        LocalDateTime.now(),
                        1000000), "계정1")
        );
    }

    @DisplayName("의뢰 추가 테스트")
    @ParameterizedTest
    @MethodSource("newRequest")
    @Order(0)
    public void testCreateRequest(ProjectRegistRequestDTO projectRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> projectService.registProject(projectRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 의뢰 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindRequestByRequestId(long questionId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.findProjectByProjectId(questionId)));
    }

    @DisplayName("계정 ID에 따른 의뢰 paging 조회 테스트")
    @CsvSource({"0, 계정1", "0, 계정2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByAccountId(int page, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.findProjectsByAccountIdWithPaging(page, accountId)));
    }

    @DisplayName("의뢰 수정 테스트")
    @MethodSource("modifiedRequest")
    @ParameterizedTest
    @Order(3)
    public void testUpdateRequest(ProjectUpdateRequestDTO ProjectUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(projectService.updateProject(ProjectUpdateRequestDTO, accountId)));
    }

    @DisplayName("의뢰 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(4)
    public void testDeleteRequest(long requestId) {
        Assertions.assertDoesNotThrow(() ->
                projectService.deleteProject(requestId));
    }

    @DisplayName("업무 범위에 따른 의뢰 조회 테스트")
    @CsvSource({"0, 업무범위1", "0, 업무범위2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByWorkScope(int page, String workScope) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.findProjectsByWorkScopeWithPaging(page, workScope)));
    }

    @DisplayName("근무 형태에 따른 의뢰 조회 테스트")
    @CsvSource({"0, 근무형태1", "0, 근무형태2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByWorkType(int page, String workType) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.findProjectsByWorkTypeWithPaging(page, workType)));
    }

    @DisplayName("진행 분류에 따른 의뢰 조회 테스트")
    @CsvSource({"0, 진행분류1", "0, 진행분류2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByProgressClassification(int page, String progressClassification) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.
                        findProjectsByProgressClassificationWithPaging(page, progressClassification)));
    }

    @DisplayName("프로젝트 주제(제목)에 따른 의뢰 조회 테스트")
    @CsvSource({"0, 제목1", "0, 제목2"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsByTitleContainingIgnoreCase(int page, String title) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService
                        .findProjectsByTitleContainingIgnoreCaseWithPaging(page, title)));
    }

    @DisplayName("필요 직군보다 더 많이 모집하는 의뢰 조회 테스트")
    @CsvSource({"0,2,2,3,1,3", "0,3,2,1,1,3"})
    @ParameterizedTest
    @Order(2)
    public void testFindRequestsWithLargerRequirements(int page, Integer requiredClient,
                                                       Integer requiredServer, Integer requiredDesign,
                                                       Integer requiredPlanner, Integer requiredAIEngineer) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Request = " + projectService.findProjectsWithLargerRequirementsWithPaging(
                        page, requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer
                )));
    }
}
