package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.character.dto.request.UserCharacterRegistRequestDTO;
import com.mtvs.devlinkbackend.character.dto.request.UserCharacterUpdateRequestDTO;
import com.mtvs.devlinkbackend.character.entity.CharacterPicture;
import com.mtvs.devlinkbackend.character.entity.CustomInfo;
import com.mtvs.devlinkbackend.character.service.UserCharacterService;
import com.mtvs.devlinkbackend.character.service.UserCharacterViewService;
import jakarta.transaction.Transactional;
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

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class UserCharacterCRUDTest {
    @Autowired
    private UserCharacterService userCharacterService;
    @Autowired
    private UserCharacterViewService userCharacterViewService;

    private static Stream<Arguments> registUserCharacter() {
        return Stream.of(
                Arguments.of(new UserCharacterRegistRequestDTO(
                        1L,
                        2L,
                        List.of(1L, 2L),
                        List.of(new CustomInfo(1, "", "", 1),
                                new CustomInfo(1, "", "", 1)),
                        new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
                ), "계정3"),
                Arguments.of(new UserCharacterRegistRequestDTO(
                        1L,
                        2L,
                        List.of(1L, 2L),
                        List.of(new CustomInfo(1, "", "", 1),
                                new CustomInfo(1, "", "", 1)),
                        new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
                ), "계정4")
        );
    }

    private static Stream<Arguments> modifyUserCharacter() {
        return Stream.of(
                Arguments.of(new UserCharacterUpdateRequestDTO(
                        1L,
                        2L,
                        List.of(1L, 2L),
                        List.of(new CustomInfo(1, "", "", 1),
                                new CustomInfo(1, "", "", 1)),
                        new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
                ), "계정1"),
                Arguments.of(new UserCharacterUpdateRequestDTO(
                        1L,
                        2L,
                        List.of(1L, 2L),
                        List.of(new CustomInfo(1, "", "", 1),
                                new CustomInfo(1, "", "", 1)),
                        new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
                ), "계정2")
        );
    }

    @BeforeEach
    public void setUp() {
        userCharacterService.registCharacter(new UserCharacterRegistRequestDTO(
                1L,
                2L,
                List.of(1L, 2L),
                List.of(new CustomInfo(1, "", "", 1),
                        new CustomInfo(1, "", "", 1)),
                new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
        ), "계정1");
        userCharacterService.registCharacter(new UserCharacterRegistRequestDTO(
                1L,
                2L,
                List.of(1L, 2L),
                List.of(new CustomInfo(1, "", "", 1),
                        new CustomInfo(1, "", "", 1)),
                new CharacterPicture(1.0F, 1.0F, 1.0F, 1.0F)
        ), "계정2");
    }

    @Order(1)
    @DisplayName("UserCharacter 등록")
    @MethodSource("registUserCharacter")
    @ParameterizedTest
    public void testRegistUserCharacter(UserCharacterRegistRequestDTO userCharacterRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> userCharacterService.registCharacter(userCharacterRegistRequestDTO, accountId));
    }

    @Order(2)
    @DisplayName("계정 ID로 UserCharacter 조회")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testFindUserCharacterByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userCharacterViewService.findCharacterByAccountId(accountId));
    }

    @Order(3)
    @DisplayName("UserCharacter 수정")
    @MethodSource("modifyUserCharacter")
    @ParameterizedTest
    public void testUpdateUserCharacter(UserCharacterUpdateRequestDTO userCharacterUpdateRequestDTO) {
        Assertions.assertDoesNotThrow(() -> userCharacterService.updateCharacter(userCharacterUpdateRequestDTO));
    }

    @Order(4)
    @DisplayName("UserCharacter 삭제")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    public void testDeleteByAccountId(String accountId) {
        Assertions.assertDoesNotThrow(() -> userCharacterService.deleteCharacterByAccountId(accountId));
    }

    @Order(2)
    @DisplayName("캐릭터 ID로 UserCharacter 조회")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindUserCharacterByUserId(Long characterId) {
        Assertions.assertDoesNotThrow(() -> userCharacterViewService.findCharacterByCharacterId(characterId));
    }

    @Order(2)
    @DisplayName("유저 ID로 UserCharacter 조회")
    @ValueSource(longs = {1, 2})
    @ParameterizedTest
    public void testFindUserCharacterByAccountId(Long userId) {
        Assertions.assertDoesNotThrow(() -> userCharacterViewService.findCharacterByUserId(userId));
    }
}
