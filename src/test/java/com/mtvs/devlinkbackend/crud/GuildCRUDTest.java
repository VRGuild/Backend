package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.guild.service.GuildService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GuildCRUDTest {
    @Autowired
    private GuildService guildService;

    @Autowired
    private GuildRepository guildRepository;

    private GuildSingleResponseDTO guild;

    @BeforeEach
    public void setUp() {
        guild = guildService.createGuild(
                new GuildRegistRequestDTO("테스트 길드", "테스트 길드 소개", 10L, List.of("member1", "member2")),
                "testOwner"
        );
    }

    @Test
    public void testCreateGuild() {
        assertNotNull(guild);
        assertEquals("테스트 길드", guild.getData().getGuildName());
        assertEquals("testOwner", guild.getData().getOwnerId());
    }

    @Test
    public void testFindGuildByGuildId() {
        GuildSingleResponseDTO foundGuild = guildService.findGuildByGuildId(guild.getData().getGuildId());
        assertNotNull(foundGuild);
        assertEquals(guild.getData().getGuildId(), foundGuild.getData().getGuildId());
    }

    @Test
    public void testFindGuildsByGuildNameContaining() {
        GuildListResponseDTO foundGuilds = guildService.findGuildsByGuildNameContaining("테스트");
        assertFalse(foundGuilds.getData().isEmpty());
        assertTrue(foundGuilds.getData().stream().anyMatch(g -> g.getGuildName().contains("테스트")));
    }

    @Test
    public void testFindGuildsByOwnerId() {
        GuildListResponseDTO foundGuilds = guildService.findGuildsByOwnerId("testOwner");
        assertFalse(foundGuilds.getData().isEmpty());
        assertTrue(foundGuilds.getData().stream().anyMatch(g -> g.getOwnerId().equals("testOwner")));
    }

    @Test
    public void testUpdateGuild() {
        GuildUpdateRequestDTO updateRequest = new GuildUpdateRequestDTO(
                guild.getData().getGuildId(), "업데이트 길드 이름", "업데이트 소개", 15L, List.of("member1"), 1L
        );

        GuildSingleResponseDTO updatedGuild = guildService.updateGuild(updateRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertEquals("업데이트 길드 이름", updatedGuild.getData().getGuildName());
        assertEquals(1L, updatedGuild.getData().getChannelId());
    }

    @Test
    public void testAddMemberToGuild() {
        GuildMemberModifyRequestDTO addRequest = new GuildMemberModifyRequestDTO(
                guild.getData().getGuildId(), List.of("newMember")
        );

        GuildSingleResponseDTO updatedGuild = guildService.addMemberToGuild(addRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertTrue(updatedGuild.getData().getMemberList().contains("newMember"));
    }

    @Test
    public void testRemoveMemberToGuild() {
        GuildMemberModifyRequestDTO removeRequest = new GuildMemberModifyRequestDTO(
                guild.getData().getGuildId(), List.of("member1")
        );

        GuildSingleResponseDTO updatedGuild = guildService.removeMemberToGuild(removeRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertFalse(updatedGuild.getData().getMemberList().contains("member1"));
    }

    @Test
    public void testDeleteGuild() {
        guildService.deleteGuild(guild.getData().getGuildId());
        GuildSingleResponseDTO deletedGuild = guildService.findGuildByGuildId(guild.getData().getGuildId());
        assertNull(deletedGuild);
    }
}
