package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.sub.GuildAndMemberDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.guild.service.GuildService;
import com.mtvs.devlinkbackend.guild.service.GuildViewService;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GuildCRUDTest {
    @Autowired
    private GuildService guildService;

    @Autowired
    private GuildViewService guildViewService;

    @Test
    @DisplayName("Guild 생성 테스트")
    void createGuildTest() {
        GuildRegistRequestDTO requestDTO = new GuildRegistRequestDTO("Test Guild", "Test Guild Introduction", 1L, 1000);
        var responseDTO = guildService.createGuild(requestDTO);
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getGuildName()).isEqualTo("Test Guild");
    }

    @Test
    @DisplayName("Guild 수정 테스트")
    void updateGuildTest() {
        GuildRegistRequestDTO requestDTO = new GuildRegistRequestDTO("Test Guild", "Test Guild Introduction", 1L, 1000);
        var responseDTO = guildService.createGuild(requestDTO);

        GuildUpdateRequestDTO updateRequestDTO = new GuildUpdateRequestDTO(
                responseDTO.getData().getGuildId(),
                "Updated Guild Name",
                "Updated Guild Introduction",
                1L,
                2000
        );

        var updatedGuildDTO = guildService.updateGuild(updateRequestDTO);
        assertThat(updatedGuildDTO).isNotNull();
        assertThat(updatedGuildDTO.getData().getGuildName()).isEqualTo("Updated Guild Name");
    }

    @Test
    @DisplayName("잘못된 Guild 수정 시도 테스트")
    void updateGuildWithInvalidMasterUserIdTest() {
        GuildRegistRequestDTO requestDTO = new GuildRegistRequestDTO("Test Guild", "Test Guild Introduction", 1L, 1000);
        var responseDTO = guildService.createGuild(requestDTO);

        GuildUpdateRequestDTO updateRequestDTO = new GuildUpdateRequestDTO(
                responseDTO.getData().getGuildId(),
                "Updated Guild Name",
                "Updated Guild Introduction",
                2L,  // 다른 masterUserId를 사용하여 수정 시도
                2000
        );

        assertThatThrownBy(() -> guildService.updateGuild(updateRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("owner가 아닌 계정으로 Guild 수정 시도");
    }

    @Test
    @DisplayName("Guild 멤버 추가 테스트")
    void applyMemberToGuildTest() {
        GuildRegistRequestDTO requestDTO = new GuildRegistRequestDTO("Test Guild", "Test Guild Introduction", 1L, 1000);
        var responseDTO = guildService.createGuild(requestDTO);

        GuildMemberModifyRequestDTO memberRequestDTO = new GuildMemberModifyRequestDTO(
                responseDTO.getData().getGuildId(),
                responseDTO.getData().getMasterUserId(),
                List.of(new Member("Guild", 1L, 2L, "motive", AcceptStatus.PENDING))
        );

        var modifiedGuildDTO = guildService.applyMemberToGuild(memberRequestDTO);
        assertThat(modifiedGuildDTO).isNotNull();
        assertThat(modifiedGuildDTO.getData().getGuildMemberList()).hasSize(1);
    }

    @Test
    @DisplayName("Guild 삭제 테스트")
    void deleteGuildTest() {
        GuildRegistRequestDTO requestDTO = new GuildRegistRequestDTO("Test Guild", "Test Guild Introduction", 1L, 1000);
        var responseDTO = guildService.createGuild(requestDTO);

        Long guildId = responseDTO.getData().getGuildId();
        guildService.deleteGuild(guildId);

        assertThrows(IllegalArgumentException.class, () -> guildViewService.findGuildDetailByGuildId(guildId));
    }
}
