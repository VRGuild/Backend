package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientGroupRequestDTO;
import com.mtvs.devlinkbackend.oauth2.service.UserClientGroupService;
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
public class UserClientGroupCRUDTest {
    @Autowired
    private UserClientGroupService userClientGroupService;

    private static Stream<Arguments> registUserClientGroup() {
        return Stream.of(
                Arguments.of(new UserClientGroupRequestDTO(
                        "UserClientGroup",
                        "팀",
                        "그룹이름3",
                        "담당자이름3",
                        "담당자핸드폰번호3"
                ), "계정3"),
                Arguments.of(new UserClientGroupRequestDTO(
                        "UserClientGroup",
                        "팀2",
                        "그룹이름4",
                        "담당자이름4",
                        "담당자핸드폰번호4"
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyUserClientGroup() {
        return Stream.of(
                Arguments.of(new UserClientGroupRequestDTO(
                        "UserClientGroup",
                        "팀",
                        "그룹이름2",
                        "담당자이름2",
                        "담당자핸드폰번호2"
                ), "계정1"),
                Arguments.of(new UserClientGroupRequestDTO(
                        "UserClientGroup",
                        "팀",
                        "그룹이름1",
                        "담당자이름1",
                        "담당자핸드폰번호1"
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() {
        userClientGroupService.registUserClientGroup(new UserClientGroupRequestDTO(
                "UserClientGroup",
                "팀",
                "그룹이름1",
                "담당자이름1",
                "담당자핸드폰번호1"
        ), "계정1");
        userClientGroupService.registUserClientGroup(new UserClientGroupRequestDTO(
                "UserClientGroup",
                "팀",
                "그룹이름2",
                "담당자이름2",
                "담당자핸드폰번호2"
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserClientGroup 등록")
    @MethodSource("registUserClientGroup")
    @ParameterizedTest
    public void testRegistUserClientGroup(UserClientGroupRequestDTO UserClientGroupRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.registUserClientGroup(UserClientGroupRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 UserClientGroup 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindUserClientGroupByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.findUserClientGroupByAccountId(accountId));
    }

    @Order(3)
    @DisplayName("담당자이름으로 UserClientGroup 조회")
    @ValueSource(strings = {"담당자이름1", "담당자이름2"})
    @ParameterizedTest
    public void findByManagerNameContainingIgnoreCase(String managerName) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.findByManagerNameContainingIgnoreCase(managerName));
    }

    @Order(4)
    @DisplayName("그룹이름으로 UserClientGroup 조회")
    @ValueSource(strings = {"그룹이름1", "그룹이름2"})
    @ParameterizedTest
    public void testFindByGroupNameContainingIgnoreCase(String groupName) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.findByGroupNameContainingIgnoreCase(groupName));
    }

    @Order(5)
    @DisplayName("이메일로 UserClientGroup 조회")
    @ValueSource(strings = {"팀", "법인"})
    @ParameterizedTest
    public void testFindUserClientGroupsByEmail(String clientType) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.findByClientType(clientType));
    }

    @Order(6)
    @DisplayName("담당자 핸드폰 번호로 UserClientGroup 조회")
    @ValueSource(strings = {"담당자핸드폰1", "담당자핸드폰2"})
    @ParameterizedTest
    public void testFindUserClientGroupByPhone(String managerPhone) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.findByManagerPhone(managerPhone));
    }

    @Order(7)
    @DisplayName("UserClientGroup 수정")
    @MethodSource("modifyUserClientGroup")
    @ParameterizedTest
    public void testUpdateUserClientGroup(UserClientGroupRequestDTO UserClientGroupRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.updateUserClientGroup(UserClientGroupRequestDTO, accountId));
    }

    @Order(8)
    @DisplayName("UserClientGroup 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userClientGroupService.deleteByAccountId(accountId));
    }
}
