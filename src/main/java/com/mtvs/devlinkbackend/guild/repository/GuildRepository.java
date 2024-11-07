package com.mtvs.devlinkbackend.guild.repository;

import com.mtvs.devlinkbackend.guild.entity.Guild;
import com.mtvs.devlinkbackend.guild.repository.projection.Guild_GuildName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
}
