package com.mtvs.devlinkbackend.member.repository;

import com.mtvs.devlinkbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteMembersByMemberIdIn(List<Long> memberIdList);
}
