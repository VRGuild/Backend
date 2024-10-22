package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.request.ChannelUpdateRequestDTO;
import com.mtvs.devlinkbackend.channel.entity.PositionType;
import com.mtvs.devlinkbackend.channel.service.ChannelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class ChannelCRUDTest {

    @Autowired
    private ChannelService channelService;

    private static Stream<Arguments> newChannel() {
        return Stream.of(
                Arguments.of(new ChannelRegistRequestDTO(Arrays.asList(new PositionType(new PositionType.Position(1, 2, 3), "chair"))), "0bb31d25962c4817be894044371d4d3c"),
                Arguments.of(new ChannelRegistRequestDTO(Arrays.asList(new PositionType(new PositionType.Position(4, 5, 6), "grass"))), "0bb31d25962c4817be894044371d4d3c")
                );
    }

    private static Stream<Arguments> modifiedChannel() {
        return Stream.of(
                Arguments.of(new ChannelUpdateRequestDTO("27ceb93e-bf35-4058-9a07-46550cf1aa00", Arrays.asList(new PositionType(new PositionType.Position(1, 2, 3), "chair"))), "0bb31d25962c4817be894044371d4d3c"),
                Arguments.of(new ChannelUpdateRequestDTO("27ceb93e-bf35-4058-9a07-46550cf1aa00", Arrays.asList(new PositionType(new PositionType.Position(3, 1, 2), "block"))), "0bb31d25962c4817be894044371d4d3c")
        );
    }

    @DisplayName("채널 등록 테스트")
    @ParameterizedTest
    @MethodSource("newChannel")
    public void testCreateChannel(ChannelRegistRequestDTO channelRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> channelService.saveChannel(channelRegistRequestDTO, accountId));
    }

    @DisplayName("채널 PK로 조회 테스트")
    @ValueSource(strings = {"79673000-2800-4226-ac32-fae65cd0a55c"})
    @ParameterizedTest
    public void testFindChannelByChannelId(String channelId) {
        Assertions.assertDoesNotThrow(() -> channelService.findChannelByChannelId(channelId));
    }

    @DisplayName("모든 채널 조회 테스트")
    @Test
    public void testFindAllChannels() {
        Assertions.assertDoesNotThrow(() -> channelService.findAllChannels());
    }

    @DisplayName("채널 수정 테스트")
    @MethodSource("modifiedChannel")
    @ParameterizedTest
    public void testUpdateChannel(ChannelUpdateRequestDTO channelUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> channelService.updateChannel(channelUpdateRequestDTO, accountId));
    }

    @DisplayName("채널 삭제 테스트")
    @ValueSource(strings = {"79673000-2800-4226-ac32-fae65cd0a55c"})
    @ParameterizedTest
    public void testDeleteChannel(String channelId) {
        Assertions.assertDoesNotThrow(() -> channelService.deleteChannel(channelId));
    }
}
