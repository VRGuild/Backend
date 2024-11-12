package com.mtvs.devlinkbackend.member.command.repository;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteMembersByMemberIdIn(List<Long> memberIdList);
    Member findByAssigneesIdAndUserId(Long assigneesId, Long userId);
}
