package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserClientIndividualRequestDTO;
import com.mtvs.devlinkbackend.oauth2.service.UserClientIndividualService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class UserClientIndividualCRUDTest {

    @Autowired
    private UserClientIndividualService userClientIndividualService;

    private static Stream<Arguments> registUserClientIndividual() {
        return Stream.of(
                Arguments.of(new UserClientIndividualRequestDTO(
                        "UserClientIndividual",
                        "이름3",
                        "핸드폰번호3"
                ), "계정3"),
                Arguments.of(new UserClientIndividualRequestDTO(
                        "UserClientIndividual",
                        "이름4",
                        "핸드폰번호4"
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyUserClientIndividual() {
        return Stream.of(
                Arguments.of(new UserClientIndividualRequestDTO(
                        "UserClientIndividual",
                        "이름2",
                        "핸드폰번호2"
                ), "계정1"),
                Arguments.of(new UserClientIndividualRequestDTO(
                        "UserClientIndividual",
                        "이름1",
                        "핸드폰번호1"
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() {
        userClientIndividualService.registUserClientIndividual(new UserClientIndividualRequestDTO(
                "UserClientIndividual",
                "이름1",
                "핸드폰번호1"
        ), "계정1");
        userClientIndividualService.registUserClientIndividual(new UserClientIndividualRequestDTO(
                "UserClientIndividual",
                "이름2",
                "핸드폰번호2"
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserClientIndividual 등록")
    @MethodSource("registUserClientIndividual")
    @ParameterizedTest
    public void testRegistUserClientIndividual(UserClientIndividualRequestDTO UserClientIndividualRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.registUserClientIndividual(UserClientIndividualRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 UserClientIndividual 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindUserClientIndividualByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.findUserClientIndividualByAccountId(accountId));
    }

    @Order(3)
    @DisplayName("이름으로 UserClientIndividual 조회")
    @ValueSource(strings = {"이름1", "이름2"})
    @ParameterizedTest
    public void findByManagerNameContainingIgnoreCase(String name) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.findUserClientIndividualsByNameContainingIgnoreCase(name));
    }

    @Order(4)
    @DisplayName("핸드폰 번호로 UserClientIndividual 조회")
    @ValueSource(strings = {"핸드폰1", "핸드폰2"})
    @ParameterizedTest
    public void testFindUserClientIndividualByPhone(String phone) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.findUserClientIndividualsByPhone(phone));
    }

    @Order(5)
    @DisplayName("UserClientIndividual 수정")
    @MethodSource("modifyUserClientIndividual")
    @ParameterizedTest
    public void testUpdateUserClientIndividual(UserClientIndividualRequestDTO UserClientIndividualRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.updateUserClientIndividual(UserClientIndividualRequestDTO, accountId));
    }

    @Order(6)
    @DisplayName("UserClientIndividual 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientIndividualService.deleteByAccountId(accountId));
    }
}
