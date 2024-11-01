package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEpicAccountId(String epicAccountId);
    void deleteByEpicAccountId(String epicAccountId);
}
