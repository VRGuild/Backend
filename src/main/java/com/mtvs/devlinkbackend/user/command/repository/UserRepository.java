package com.mtvs.devlinkbackend.user.command.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUserId(Long userId);
}
