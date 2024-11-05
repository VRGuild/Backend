package com.mtvs.devlinkbackend.member.command.service;

import com.mtvs.devlinkbackend.member.command.model.entity.AcceptStatus;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.repository.MemberRepository;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registAll(List<Member> memberList) {
        memberRepository.saveAll(memberList);
    }

    @Transactional
    public void deleteAll(List<Long> memberIdList) {
        memberRepository.deleteMembersByMemberIdIn(memberIdList);
    }

    @Transactional
    public MemberStatusResponseDTO acceptSupplyByMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            Member foundMember = member.get();
            foundMember.setIsAccepted(AcceptStatus.ACCEPTED);
            return new MemberStatusResponseDTO(foundMember);
        }
        return null;
    }

    @Transactional
    public MemberStatusResponseDTO rejectSupplyByMemberId(Long memberId) {
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
}
