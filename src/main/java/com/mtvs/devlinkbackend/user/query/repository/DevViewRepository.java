package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevViewRepository extends JpaRepository<Dev, Long> {
    Dev findDevByUserId(Long userId);

    Dev findDevByDevPhone(String devPhone);

    List<Dev> findDevsByDevNameContainingIgnoreCase(String devName);

    List<Dev> findDevsByUser_Nickname(String nickname);

    List<Dev> findDevsByDevEmail(String devEmail);
}
