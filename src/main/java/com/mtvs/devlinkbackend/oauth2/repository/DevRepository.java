package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.Dev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevRepository extends JpaRepository<Dev, Long> {
    Dev findDevByUser_EpicAccountId(String epicAccountId);

    Dev findDevByDevPhone(String devPhone);

    List<Dev> findDevsByDevNameContainingIgnoreCase(String devName);

    List<Dev> findDevsByUser_Nickname(String nickname);

    List<Dev> findDevsByDevEmail(String devEmail);

    void deleteDevByUser_EpicAccountId(String epicAccountId);
}
