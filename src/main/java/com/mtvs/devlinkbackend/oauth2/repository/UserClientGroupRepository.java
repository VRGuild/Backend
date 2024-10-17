package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.UserClientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClientGroupRepository extends JpaRepository<UserClientGroup, Long> {
    UserClientGroup findUserClientGroupByAccountId(String accountId);

    List<UserClientGroup> findByManagerNameContainingIgnoreCase(String managerName);

    List<UserClientGroup> findByGroupNameContainingIgnoreCase(String groupName);

    List<UserClientGroup> findByClientType(String clientType);

    List<UserClientGroup> findByManagerPhone(String managerPhone);

    void deleteByAccountId(String accountId);
}
