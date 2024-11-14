package com.mtvs.devlinkbackend.member.command.service;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.repository.MemberRepository;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserViewService userViewService;

    public MemberService(MemberRepository memberRepository, UserViewService userViewService) {
        this.memberRepository = memberRepository;
        this.userViewService = userViewService;
    }

    @Transactional
    public void registAll(List<Member> memberList) {
        memberRepository.saveAll(memberList);
    }

    @Transactional
    public Member regist(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteAll(List<Long> memberIdList) {
        memberRepository.deleteMembersByMemberIdIn(memberIdList);
    }

    @Transactional
    public MemberStatusResponseDTO acceptSupplyByMemberId(Long memberId, String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("등록되지 않은 유저가 지원 수락 시도중");

        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            Member foundMember = member.get();
            foundMember.setIsAccepted(AcceptStatus.ACCEPTED);
            return new MemberStatusResponseDTO(foundMember);
        }
        return null;
    }

    @Transactional
    public MemberStatusResponseDTO rejectSupplyByMemberId(Long memberId, String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("등록되지 않은 유저가 지원 거절 시도중");

        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            Member foundMember = member.get();
            foundMember.setIsAccepted(AcceptStatus.REJECTED);
            return new MemberStatusResponseDTO(foundMember);
        }
        return null;
    }

    public MemberStatusResponseDTO deleteMemberByMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            Member foundMember = member.get();
            foundMember.setIsAccepted(AcceptStatus.DELETED);
            return new MemberStatusResponseDTO(foundMember);
        }
        return null;
    }
    public Boolean isMemberExist(Long assigneesId, Long userId, Long groupId, String type) {
            Member member = memberRepository.findByAssigneesIdAndUserIdAndGroupIdAndType(assigneesId, userId, groupId, type);
            return member != null;
    }
}
