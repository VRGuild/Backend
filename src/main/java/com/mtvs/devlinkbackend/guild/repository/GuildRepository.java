package com.mtvs.devlinkbackend.guild.repository;

import com.mtvs.devlinkbackend.guild.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    List<Guild> findGuildsByGuildNameContaining(String guildName);
    List<Guild> findGuildsByOwnerId(String ownerId);
    @Query("SELECT g FROM Guild g JOIN g.memberList m WHERE m LIKE :memberId")
    List<Guild> findGuildsByMemberIdContaining(@Param("memberId") String accountId);
}
