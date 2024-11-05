package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.user.command.model.dto.request.BusinessRequestDTO;
import com.mtvs.devlinkbackend.user.command.service.EpicBusinessService;
import com.mtvs.devlinkbackend.user.query.service.EpicBusinessViewService;
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
public class BusinessCRUDTest {
    @Autowired
    private EpicBusinessService epicBusinessService;
    @Autowired
    private EpicBusinessViewService epicBusinessViewService;

    private static Stream<Arguments> registUserClientGroup() {
        return Stream.of(
                Arguments.of(new BusinessRequestDTO(
                        "UserClientGroup",
                        "팀",
                        null,
                        "담당자이름3",
                        "담당자핸드폰번호3"
                ), "계정3"),
                Arguments.of(new BusinessRequestDTO(
                        "UserClientGroup",
                        "팀2",
                        null,
                        "담당자이름4",
                        "담당자핸드폰번호4"
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyUserClientGroup() {
        return Stream.of(
                Arguments.of(new BusinessRequestDTO(
                        "UserClientGroup",
                        "팀",
                        null,
                        "담당자이름2",
                        "담당자핸드폰번호2"
                ), "계정1"),
                Arguments.of(new BusinessRequestDTO(
                        "UserClientGroup",
                        "팀",
                        null,
                        "담당자이름1",
                        "담당자핸드폰번호1"
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() {
        epicBusinessService.registUserClientGroup(new BusinessRequestDTO(
                "UserClientGroup",
                "팀",
                null,
                "담당자이름1",
                "담당자핸드폰번호1"
        ), "계정1");
        epicBusinessService.registUserClientGroup(new BusinessRequestDTO(
                "UserClientGroup",
                "팀",
                null,
                "담당자이름2",
                "담당자핸드폰번호2"
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserClientGroup 등록")
    @MethodSource("registUserClientGroup")
    @ParameterizedTest
    public void testRegistUserClientGroup(BusinessRequestDTO BusinessRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> epicBusinessService.registUserClientGroup(BusinessRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 UserClientGroup 조회")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindUserClientGroupByAccountId(Long businessId) {
        Assertions.assertDoesNotThrow(() -> epicBusinessViewService.findBusinessByBusinessId(businessId));
    }

    @Order(7)
    @DisplayName("UserClientGroup 수정")
    @MethodSource("modifyUserClientGroup")
    @ParameterizedTest
    public void testUpdateUserClientGroup(BusinessRequestDTO BusinessRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> epicBusinessService.updateUserClientGroup(BusinessRequestDTO, accountId));
    }

    @Order(8)
    @DisplayName("UserClientGroup 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> epicBusinessService.deleteByAccountId(accountId));
    }
}
