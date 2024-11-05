package com.mtvs.devlinkbackend.member.service;

import com.mtvs.devlinkbackend.member.entity.Member;
import com.mtvs.devlinkbackend.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
