package com.mtvs.devlinkbackend.team.repository;

import com.mtvs.devlinkbackend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamViewRepository extends JpaRepository<Team, Long> {
    List<Team> findTeamsByLeaderUserId(Long leaderUserId);

    // teamMemberList에 memberId를 포함하는 팀 조회
    @Query("SELECT t FROM Team t WHERE :memberId IN (t.teamMemberList)")
    List<Team> findByMemberIdInTeam(@Param("memberId") Long memberId);
}
