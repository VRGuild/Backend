package com.mtvs.devlinkbackend.member.query.service;

import com.mtvs.devlinkbackend.member.query.repository.MemberViewRepository;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberViewService {
    private final MemberViewRepository memberViewRepository;

    public MemberViewService(MemberViewRepository memberViewRepository) {
        this.memberViewRepository = memberViewRepository;
    }

    public MemberStatusResponseDTO findMemberByMemberId(Long memberId) {
        return new MemberStatusResponseDTO(memberViewRepository.findById(memberId).orElse(null));
    }
}
