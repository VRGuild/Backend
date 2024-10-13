package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.ether.dto.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.service.EtherService;
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
public class EtherCRUDTest {
    @Autowired
    private EtherService etherService;

    private static Stream<Arguments> newEther() {
        return Stream.of(
                Arguments.of(new EtherRegistRequestDTO(10L, "이유0"), "계정1"),
                Arguments.of(new EtherRegistRequestDTO(100L, "이유00"), "계정2")
        );
    }

    private static Stream<Arguments> modifiedEther() {
        return Stream.of(
                Arguments.of(new EtherUpdateRequestDTO(1L,200L, "이유0")),
                Arguments.of(new EtherUpdateRequestDTO(2L,500L , "이유00"))
        );
    }

    @DisplayName("에테르 이력 추가 테스트")
    @ParameterizedTest
    @MethodSource("newEther")
    @Order(0)
    public void testCreateEther(EtherRegistRequestDTO etherRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> etherService.registEther(etherRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 에테르 이력 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindEtherByEtherId(long etherId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Ether = " + etherService.findEtherByEtherId(etherId)));
    }

    @DisplayName("계정 ID에 따른 에테르 이력 조회 테스트")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    @Order(3)
    public void testFindEthersByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Ether = " + etherService.findEthersByAccountId(accountId)));
    }

    @DisplayName("지급 이유에 따른 에테르 이력 조회 테스트")
    @ValueSource(strings = {"Salary Bonus", "Expense Compensation"})
    @ParameterizedTest
    @Order(4)
    public void testFindEthersByReason(String reason) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Ether = " + etherService.findEthersByReason(reason)));
    }

    @DisplayName("에테르 이력 수정 테스트")
    @MethodSource("modifiedEther")
    @ParameterizedTest
    @Order(5)
    public void testUpdateQuestion(EtherUpdateRequestDTO etherUpdateRequestDTO) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(etherService.updateEther(etherUpdateRequestDTO)));
    }

    @DisplayName("에테르 이력 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(6)
    public void testDeleteEther(long etherId) {
        Assertions.assertDoesNotThrow(() ->
                etherService.deleteEtherByEtherId(etherId));
    }
}
