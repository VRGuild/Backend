package com.mtvs.devlinkbackend.team.repository;

import com.mtvs.devlinkbackend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findTeamsByTeamNameContaining(String teamName);

    List<Team> findTeamByPmId(String pmId);

    @Query("SELECT t FROM Team t JOIN t.memberList m WHERE m LIKE :memberId")
    List<Team> findTeamsByMemberIdContaining(@Param("memberId") String accountId);
}
