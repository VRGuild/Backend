package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByAccountId(String accountId);
    void deleteUserByAccountId(String accountId);
}
