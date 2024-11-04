package com.mtvs.devlinkbackend.user.command.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUserId(Long userId);
}
