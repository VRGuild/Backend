package com.mtvs.devlinkbackend.guild.service;

import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailPagingResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.GuildListResponseDTO;
import com.mtvs.devlinkbackend.guild.dto.response.sub.GuildAndMemberDTO;
import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.GuildViewRepository;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.query.service.MemberViewService;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildViewService {

    private final static Integer PAGE_SIZE = 15;

    private final GuildViewRepository guildViewRepository;
    private final MemberViewService memberViewService;
    private final UserViewService userViewService;

    public GuildViewService(GuildViewRepository guildViewRepository, MemberViewService memberViewService, UserViewService userViewService) {
        this.guildViewRepository = guildViewRepository;
        this.memberViewService = memberViewService;
        this.userViewService = userViewService;
    }

    public GuildDetailSingleResponseDTO findGuildDetailByGuildId(Long guildId) {
        Guild guild = guildViewRepository.findById(guildId).orElse(null);

        if(guild == null)
            throw new IllegalArgumentException("잘못된 guildId로 접근");

        return new GuildDetailSingleResponseDTO(
            new GuildAndMemberDTO(
                    guildId,
                    guild.getGuildName(),
                    guild.getGuildIntroduction(),
                    guild.getMasterUserId(),
                    guild.getMaximumMember(),
                    guild.getGuildMemberList().stream().map(memberId ->
                            memberViewService.findMemberByMemberId(memberId).getData()).toList()
            )
        );
    }

    public GuildDetailPagingResponseDTO findGuildDetailsWithPagination(Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").ascending());
        Page<Guild> guildPage = guildViewRepository.findAllBy(pageable);

        List<GuildAndMemberDTO> guildAndMemberDTOList = guildPage.getContent().stream().map(guild ->
                new GuildAndMemberDTO(
                        guild.getGuildId(),
                        guild.getGuildName(),
                        guild.getGuildIntroduction(),
                        guild.getMasterUserId(),
                        guild.getMaximumMember(),
                        guild.getGuildMemberList().stream().map(memberId ->
                                memberViewService.findMemberByMemberId(memberId).getData()).toList()
        )).toList();

        return new GuildDetailPagingResponseDTO(guildAndMemberDTOList, guildPage.getTotalPages(), guildViewRepository.count());
    }

    public GuildListResponseDTO findGuildsByAccountIdInGuild(String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 에픽 계정으로 접근");

        List<Member> memberList = memberViewService.findMemberByUserId(user.getUserId());
        List<Long> memberIdList = memberList.stream().map(Member::getMemberId).toList();

        return new GuildListResponseDTO(guildViewRepository.findAllByMemberIdsInGuildMemberList(memberIdList));
    }

    public GuildListResponseDTO findGuildsByMasterAccountId(String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 에픽 계정으로 접근");

        return new GuildListResponseDTO(
                guildViewRepository.findGuildsByMasterUserId(user.getUserId())
        );
    }
}
