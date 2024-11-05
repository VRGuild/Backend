package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.user.command.model.dto.request.DevInfoRequestDTO;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevRegistRequestDTO;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevUpdateRequestDTO;
import com.mtvs.devlinkbackend.user.command.service.EpicDevService;
import com.mtvs.devlinkbackend.user.query.service.EpicDevViewService;
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

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class DevCRUDTest {

    @Autowired
    private EpicDevService epicDevService;
    @Autowired
    private EpicDevViewService epicDevViewService;

    private static Stream<Arguments> registDev() {
        return Stream.of(
                Arguments.of(new DevRegistRequestDTO(
                        "닉네임3",
                        new DevInfoRequestDTO(
                                "실명",
                                "이메일1",
                                "핸드폰",
                                "깃헙 링크",
                                null,
                                "",
                                List.of("",""),
                                List.of("",""),
                                ""
                        )
                ), "계정3"),
                Arguments.of(new DevRegistRequestDTO(
                        "닉네임3",
                        new DevInfoRequestDTO(
                                "실명",
                                "이메일1",
                                "핸드폰",
                                "깃헙 링크",
                                null,
                                "",
                                List.of("",""),
                                List.of("",""),
                                ""
                        )
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyDev() {
        return Stream.of(
                Arguments.of(new DevUpdateRequestDTO(
                        "닉네임3",
                        new DevInfoRequestDTO(
                                "실명",
                                "이메일1",
                                "핸드폰",
                                "깃헙 링크",
                                null,
                                "",
                                List.of("",""),
                                List.of("",""),
                                ""
                        )
                ), "계정1"),
                Arguments.of(new DevUpdateRequestDTO(
                        "닉네임3",
                        new DevInfoRequestDTO(
                                "실명",
                                "이메일1",
                                "핸드폰",
                                "깃헙 링크",
                                null,
                                "",
                                List.of("",""),
                                List.of("",""),
                                ""
                        )
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() throws IOException {
        epicDevService.registDev(new DevRegistRequestDTO(
                "닉네임3",
                new DevInfoRequestDTO(
                        "실명",
                        "이메일1",
                        "핸드폰",
                        "깃헙 링크",
                        null,
                        "",
                        List.of("",""),
                        List.of("",""),
                        ""
                )
        ), "계정1");
        epicDevService.registDev(new DevRegistRequestDTO(
                "닉네임3",
                new DevInfoRequestDTO(
                        "실명",
                        "이메일1",
                        "핸드폰",
                        "깃헙 링크",
                        null,
                        "",
                        List.of("",""),
                        List.of("",""),
                        ""
                )
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserPartner 등록")
    @MethodSource("registDev")
    @ParameterizedTest
    public void testRegistDev(DevRegistRequestDTO devRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> epicDevService.registDev(devRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 Dev 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindDevByEpicAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> epicDevViewService.findDevByEpicAccountId(accountId));
    }

    @Order(4)
    @DisplayName("닉네임으로 Dev 조회")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindDevByUserId(Long userId) {
        Assertions.assertDoesNotThrow(() -> epicDevViewService.findDevByUserId(userId));
    }


    @Order(7)
    @DisplayName("Dev 수정")
    @MethodSource("modifyDev")
    @ParameterizedTest
    public void testUpdateUserPartner(DevUpdateRequestDTO devRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> epicDevService.updateUserPartner(devRequestDTO, accountId));
    }

    @Order(8)
    @DisplayName("Dev 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> epicDevService.deleteByAccountId(accountId));
    }
}
