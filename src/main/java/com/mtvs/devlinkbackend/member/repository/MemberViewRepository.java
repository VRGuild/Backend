package com.mtvs.devlinkbackend.member.repository;

import com.mtvs.devlinkbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberViewRepository extends JpaRepository<Member, Long> {
}
