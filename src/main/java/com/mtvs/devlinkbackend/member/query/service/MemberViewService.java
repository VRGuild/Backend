package com.mtvs.devlinkbackend.member.query.service;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.query.repository.MemberViewRepository;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import com.mtvs.devlinkbackend.member.query.view.response.sub.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberViewService {
    private final MemberViewRepository memberViewRepository;

    public MemberViewService(MemberViewRepository memberViewRepository) {
        this.memberViewRepository = memberViewRepository;
    }

    public MemberStatusResponseDTO findMemberDTOByMemberId(Long memberId) {
        Member member = memberViewRepository.findById(memberId).orElse(null);
        if (member == null)
            throw new IllegalArgumentException("잘못된 memberId로 접근중");

        return new MemberStatusResponseDTO(
                new MemberDTO(
                        member.getMemberId(),
                        member.getType(),
                        member.getAssigneesId(),
                        member.getUserId(),
                        member.getMotive(),
                        member.getGroupId(),
                        member.getIsAccepted().getValue(),
                        member.getCreatedAt(),
                        member.getModifiedAt()
                ));
    }

    public Member findMemberByMemberId(Long memberId) {
        return memberViewRepository.findById(memberId).orElse(null);
    }

    public List<Member> findMemberByUserId(Long userId) {
        return memberViewRepository.findByUserId(userId);
    }
}
