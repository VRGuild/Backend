package com.mtvs.devlinkbackend.guild.service;

import com.mtvs.devlinkbackend.guild.dto.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuildService {

    private final GuildRepository guildRepository;

    public GuildService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Transactional
    public Guild createGuild(GuildRegistRequestDTO guildRegistRequestDTO, String accountId) {
        return guildRepository.save(new Guild(
                accountId,
                guildRegistRequestDTO.getGuildName(),
                guildRegistRequestDTO.getIntroduction(),
                guildRegistRequestDTO.getMaximumMember(),
                guildRegistRequestDTO.getMemberList()
        ));
    }

    public Guild findGuildByGuildId(Long guildId) {
        return guildRepository.findById(guildId).orElse(null);
    }

    public List<Guild> findGuildsByGuildNameContaining(String guildName) {
        return guildRepository.findGuildsByGuildNameContaining(guildName);
    }

    public List<Guild> findGuildsByOwnerId(String ownerId) {
        return guildRepository.findGuildsByOwnerId(ownerId);
    }

    public List<Guild> findGuildsByMemberIdContaining(String memberId) {
        return guildRepository.findGuildsByMemberIdContaining(memberId);
    }

    @Transactional
    public Guild updateGuild(GuildUpdateRequestDTO guildUpdateRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildUpdateRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if (foundGuild.getOwnerId().equals(accountId)) {
                foundGuild.setGuildName(guildUpdateRequestDTO.getGuildName());
                foundGuild.setIntroduction(guildUpdateRequestDTO.getIntroduction());
                foundGuild.setMaximumMember(guildUpdateRequestDTO.getMaximumMember());
                foundGuild.setMemberList(guildUpdateRequestDTO.getMemberList());
                foundGuild.setChannelId(guildUpdateRequestDTO.getChannelId());
                return foundGuild;
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    @Transactional
    public Guild addMemberToGuild(GuildMemberModifyRequestDTO guildMemberModifyRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildMemberModifyRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if(foundGuild.getOwnerId().equals(accountId)) {
                for(String memberId : foundGuild.getMemberList()) {
                    foundGuild.addMemberList(memberId);
                }
                return foundGuild;
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    @Transactional
    public Guild removeMemberToGuild(GuildMemberModifyRequestDTO guildMemberModifyRequestDTO, String accountId) {
        Optional<Guild> guild = guildRepository.findById(guildMemberModifyRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            if(foundGuild.getOwnerId().equals(accountId)) {
                for(String memberId : foundGuild.getMemberList()) {
                    foundGuild.removeMemberList(memberId);
                }
                return foundGuild;
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    public void deleteGuild(Long guildId) {
        guildRepository.deleteById(guildId);
    }
}
