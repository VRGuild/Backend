package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.guild.dto.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
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

    private Guild guild;

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
        assertEquals("테스트 길드", guild.getGuildName());
        assertEquals("testOwner", guild.getOwnerId());
    }

    @Test
    public void testFindGuildByGuildId() {
        Guild foundGuild = guildService.findGuildByGuildId(guild.getGuildId());
        assertNotNull(foundGuild);
        assertEquals(guild.getGuildId(), foundGuild.getGuildId());
    }

    @Test
    public void testFindGuildsByGuildNameContaining() {
        List<Guild> foundGuilds = guildService.findGuildsByGuildNameContaining("테스트");
        assertFalse(foundGuilds.isEmpty());
        assertTrue(foundGuilds.stream().anyMatch(g -> g.getGuildName().contains("테스트")));
    }

    @Test
    public void testFindGuildsByOwnerId() {
        List<Guild> foundGuilds = guildService.findGuildsByOwnerId("testOwner");
        assertFalse(foundGuilds.isEmpty());
        assertTrue(foundGuilds.stream().anyMatch(g -> g.getOwnerId().equals("testOwner")));
    }

    @Test
    public void testUpdateGuild() {
        GuildUpdateRequestDTO updateRequest = new GuildUpdateRequestDTO(
                guild.getGuildId(), "업데이트 길드 이름", "업데이트 소개", 15L, List.of("member1"), 1L
        );

        Guild updatedGuild = guildService.updateGuild(updateRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertEquals("업데이트 길드 이름", updatedGuild.getGuildName());
        assertEquals(1L, updatedGuild.getChannelId());
    }

    @Test
    public void testAddMemberToGuild() {
        GuildMemberModifyRequestDTO addRequest = new GuildMemberModifyRequestDTO(
                guild.getGuildId(), List.of("newMember")
        );

        Guild updatedGuild = guildService.addMemberToGuild(addRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertTrue(updatedGuild.getMemberList().contains("newMember"));
    }

    @Test
    public void testRemoveMemberToGuild() {
        GuildMemberModifyRequestDTO removeRequest = new GuildMemberModifyRequestDTO(
                guild.getGuildId(), List.of("member1")
        );

        Guild updatedGuild = guildService.removeMemberToGuild(removeRequest, "testOwner");
        assertNotNull(updatedGuild);
        assertFalse(updatedGuild.getMemberList().contains("member1"));
    }

    @Test
    public void testDeleteGuild() {
        guildService.deleteGuild(guild.getGuildId());
        Guild deletedGuild = guildService.findGuildByGuildId(guild.getGuildId());
        assertNull(deletedGuild);
    }
}
