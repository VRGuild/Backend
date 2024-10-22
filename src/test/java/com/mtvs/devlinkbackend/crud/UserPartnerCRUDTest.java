package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserPartnerRequestDTO;
import com.mtvs.devlinkbackend.oauth2.service.UserPartnerService;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class UserPartnerCRUDTest {

    @Autowired
    private UserPartnerService userPartnerService;

    private static Stream<Arguments> registUserPartner() {
        return Stream.of(
                Arguments.of(new UserPartnerRequestDTO(
                        "UserClientPartner",
                        "닉네임3",
                        "이름3",
                        "이메일3",
                        "핸드폰3",
                        List.of("http://s","http://w"),
                        "경험3",
                        Map.of("Java",3),
                        "하고싶은말3"
                ), "계정3"),
                Arguments.of(new UserPartnerRequestDTO(
                        "UserClientPartner",
                        "닉네임4",
                        "이름4",
                        "이메일4",
                        "핸드폰4",
                        List.of("http://s","http://w"),
                        "경험4",
                        Map.of("Java",1),
                        "하고싶은말4"
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyUserPartner() {
        return Stream.of(
                Arguments.of(new UserPartnerRequestDTO(
                        "UserClientPartner",
                        "닉네임1",
                        "이름1",
                        "이메일1",
                        "핸드폰1",
                        List.of("http://s","http://w"),
                        "경험1",
                        Map.of("Java",3),
                        "하고싶은말1"
                ), "계정1"),
                Arguments.of(new UserPartnerRequestDTO(
                        "UserClientPartner",
                        "닉네임2",
                        "이름2",
                        "이메일2",
                        "핸드폰2",
                        List.of("http://s","http://w"),
                        "경험2",
                        Map.of("Java",1),
                        "하고싶은말2"
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() {
        userPartnerService.registUserPartner(new UserPartnerRequestDTO(
                "UserClientPartner",
                "닉네임1",
                "이름1",
                "이메일1",
                "핸드폰1",
                List.of("http://s","http://w"),
                "경험1",
                Map.of("Java",3),
                "하고싶은말1"
        ), "계정1");
        userPartnerService.registUserPartner(new UserPartnerRequestDTO(
                "UserClientPartner",
                "닉네임2",
                "이름2",
                "이메일2",
                "핸드폰2",
                List.of("http://s","http://w"),
                "경험2",
                Map.of("Java",1),
                "하고싶은말2"
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserPartner 등록")
    @MethodSource("registUserPartner")
    @ParameterizedTest
    public void testRegistUserPartner(UserPartnerRequestDTO userPartnerRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.registUserPartner(userPartnerRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 UserPartner 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindUserPartnerByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.findUserPartnerByAccountId(accountId));
    }

    @Order(3)
    @DisplayName("이름으로 UserPartner 조회")
    @ValueSource(strings = {"이름1", "이름2"})
    @ParameterizedTest
    public void testFindUserPartnersByNameContainingIgnoreCase(String name) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.findUserPartnersByNameContainingIgnoreCase(name));
    }

    @Order(4)
    @DisplayName("닉네임으로 UserPartner 조회")
    @ValueSource(strings = {"닉네임1", "닉네임2"})
    @ParameterizedTest
    public void testFindUserPartnersByNicknameContainingIgnoreCase(String nickname) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.findUserPartnersByNicknameContainingIgnoreCase(nickname));
    }

    @Order(5)
    @DisplayName("이메일로 UserPartner 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindUserPartnersByEmail(String email) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.findUserPartnersByEmail(email));
    }

    @Order(6)
    @DisplayName("핸드폰 번호로 UserPartner 조회")
    @ValueSource(strings = {"핸드폰1", "핸드폰2"})
    @ParameterizedTest
    public void testFindUserPartnerByPhone(String phone) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.findUserPartnerByPhone(phone));
    }

    @Order(7)
    @DisplayName("UserPartner 수정")
    @MethodSource("modifyUserPartner")
    @ParameterizedTest
    public void testUpdateUserPartner(UserPartnerRequestDTO userPartnerRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.updateUserPartner(userPartnerRequestDTO, accountId));
    }

    @Order(8)
    @DisplayName("UserPartner 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userPartnerService.deleteByAccountId(accountId));
    }
}
