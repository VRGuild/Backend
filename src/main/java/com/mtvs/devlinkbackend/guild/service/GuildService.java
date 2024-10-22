package com.mtvs.devlinkbackend.guild.service;

import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuildService {

    private final GuildRepository guildRepository;

    public GuildService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Transactional
    public GuildSingleResponseDTO createGuild(GuildRegistRequestDTO guildRegistRequestDTO, String accountId) {
        return new GuildSingleResponseDTO(guildRepository.save(new Guild(
                accountId,
                guildRegistRequestDTO.getGuildName(),
                guildRegistRequestDTO.getIntroduction(),
                guildRegistRequestDTO.getMaximumMember(),
                guildRegistRequestDTO.getMemberList()
        )));
    }

    public GuildSingleResponseDTO findGuildByGuildId(Long guildId) {
        return new GuildSingleResponseDTO(guildRepository.findById(guildId).orElse(null));
    }

    public GuildListResponseDTO findGuildsByGuildNameContaining(String guildName) {
        return new GuildListResponseDTO(guildRepository.findGuildsByGuildNameContaining(guildName));
    }

    public GuildListResponseDTO findGuildsByOwnerId(String ownerId) {
        return new GuildListResponseDTO(guildRepository.findGuildsByOwnerId(ownerId));
    }

    public GuildListResponseDTO findGuildsByMemberIdContaining(String memberId) {
        return new GuildListResponseDTO(guildRepository.findGuildsByMemberIdContaining(memberId));
    }

    @Transactional
    public GuildSingleResponseDTO updateGuild(GuildUpdateRequestDTO guildUpdateRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildUpdateRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if (foundGuild.getOwnerId().equals(accountId)) {
                foundGuild.setGuildName(guildUpdateRequestDTO.getGuildName());
                foundGuild.setIntroduction(guildUpdateRequestDTO.getIntroduction());
                foundGuild.setMaximumMember(guildUpdateRequestDTO.getMaximumMember());
                foundGuild.setMemberList(guildUpdateRequestDTO.getMemberList());
                foundGuild.setChannelId(guildUpdateRequestDTO.getChannelId());
                return new GuildSingleResponseDTO();
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    @Transactional
    public GuildSingleResponseDTO addMemberToGuild(GuildMemberModifyRequestDTO guildMemberModifyRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildMemberModifyRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if(foundGuild.getOwnerId().equals(accountId)) {
                foundGuild.getMemberList().addAll(guildMemberModifyRequestDTO.getNewMemberList());
                return new GuildSingleResponseDTO(foundGuild);
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    @Transactional
    public GuildSingleResponseDTO removeMemberToGuild(GuildMemberModifyRequestDTO guildMemberModifyRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildMemberModifyRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if(foundGuild.getOwnerId().equals(accountId)) {
                foundGuild.getMemberList().removeAll(guildMemberModifyRequestDTO.getNewMemberList());
                return new GuildSingleResponseDTO(foundGuild);
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    public void deleteGuild(Long guildId) {
        guildRepository.deleteById(guildId);
    }
}
