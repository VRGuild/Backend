package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.UserClientIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClientIndividualRepository extends JpaRepository<UserClientIndividual, Long> {
    UserClientIndividual findUserClientIndividualByAccountId(String accountId);

    List<UserClientIndividual> findUserClientIndividualsByNameContainingIgnoreCase(String name);

    List<UserClientIndividual> findUserClientIndividualsByPhone(String phone);

    void deleteByAccountId(String accountId);
}
