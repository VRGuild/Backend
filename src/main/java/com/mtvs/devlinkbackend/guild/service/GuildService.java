package com.mtvs.devlinkbackend.guild.service;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.guild.dto.request.GuildMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildRegistRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.request.GuildUpdateRequestDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.sub.GuildAndMemberDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.service.MemberService;
import com.mtvs.devlinkbackend.member.query.service.MemberViewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuildService {

    private final GuildRepository guildRepository;
    private final MemberService memberService;
    private final GuildViewService guildViewService;
    private final MemberViewService memberViewService;

    public GuildService(GuildRepository guildRepository, MemberService memberService, GuildViewService guildViewService, MemberViewService memberViewService) {
        this.guildRepository = guildRepository;
        this.memberService = memberService;
        this.guildViewService = guildViewService;
        this.memberViewService = memberViewService;
    }

    @Transactional
    public GuildSingleResponseDTO createGuild(GuildRegistRequestDTO guildRegistRequestDTO) {
        return new GuildSingleResponseDTO(guildRepository.save(new Guild(
                guildRegistRequestDTO.getMasterUserId(),
                guildRegistRequestDTO.getGuildName(),
                guildRegistRequestDTO.getGuildIntroduction(),
                1000,
                new ArrayList<>()
        )));
    }

    @Transactional
    public GuildSingleResponseDTO updateGuild(GuildUpdateRequestDTO guildUpdateRequestDTO) {
        Optional<Guild> guild = guildRepository.findById(guildUpdateRequestDTO.getGuildId());
        if (guild.isEmpty())
            throw new IllegalArgumentException("잘못된 길드 ID로 접근중");

        Guild foundGuild = guild.get();
        if (foundGuild.getMasterUserId().equals(guildUpdateRequestDTO.getMasterUserId())) {
            foundGuild.setGuildName(guildUpdateRequestDTO.getGuildName());
            foundGuild.setGuildIntroduction(guildUpdateRequestDTO.getGuildIntroduction());
            foundGuild.setMaximumMember(guildUpdateRequestDTO.getMaximumMember());

            return new GuildSingleResponseDTO(foundGuild);
        } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
    }

    @Transactional
    public GuildDetailSingleResponseDTO applyMemberToGuild(GuildMemberModifyRequestDTO guildMemberModifyRequestDTO) {
        Optional<Guild> guild = guildRepository.findById(guildMemberModifyRequestDTO.getGuildId());
        if (guild.isPresent()) {
            Guild foundGuild = guild.get();
            List<Member> memberList = guildMemberModifyRequestDTO.getGuildMemberList().stream()
                    .map(guildMember ->
                            new Member(
                                    guildMember.getType(),
                                    guildMember.getUserId(),
                                    guildMember.getAssigneesId(),
                                    guildMember.getMotive(),
                                    AcceptStatus.PENDING)).toList();

            memberService.registAll(memberList);

            foundGuild.getGuildMemberList().addAll(memberList.stream().map(Member::getMemberId).toList());

            return new GuildDetailSingleResponseDTO(
                    new GuildAndMemberDTO(
                            foundGuild.getGuildId(),
                            foundGuild.getGuildName(),
                            foundGuild.getGuildIntroduction(),
                            foundGuild.getMasterUserId(),
                            foundGuild.getMaximumMember(),
                            foundGuild.getGuildMemberList().stream().map(memberId ->
                                    memberViewService.findMemberByMemberId(memberId).getData()).toList()
                    )
            );
        } else return null;
    }

    public void deleteGuild(Long guildId) {
        guildRepository.deleteById(guildId);
    }
}
