package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewRepository extends JpaRepository<User, Long> {
    User findUserByEpicAccountId(String epicAccountId);
}
