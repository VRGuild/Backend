package com.mtvs.devlinkbackend.guild.repository;

import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.projection.Guild_GuildName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuildViewRepository extends JpaRepository<Guild, Long> {
    List<Guild> findGuildsByMasterUserId(Long masterUserId);
    Guild_GuildName findByGuildId(Long guildId);
    Page<Guild> findAllBy(Pageable pageable);

    @Query(value = "SELECT * FROM GUILD g WHERE " +
            "EXISTS (SELECT 1 FROM JSON_TABLE(g.GUILD_MEMBER_LIST, '$[*]' COLUMNS(memberId BIGINT PATH '$')) jt " +
            "WHERE jt.memberId IN :memberIds)", nativeQuery = true)
    List<Guild> findAllByMemberIdsInGuildMemberList(@Param("memberIds") List<Long> memberIds);
}
