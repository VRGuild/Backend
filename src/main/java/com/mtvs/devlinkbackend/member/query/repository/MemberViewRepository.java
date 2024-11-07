package com.mtvs.devlinkbackend.member.query.repository;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberViewRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserId(Long userId);
}
