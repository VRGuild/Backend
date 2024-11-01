package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findBusinessByUser_EpicAccountId(String epicAccountId);

    List<Business> findBusinessByManagerNameContainingIgnoreCase(String managerName);

    List<Business> findByBusinessNameContainingIgnoreCase(String businessName);

    List<Business> findByManagerPhone(String managerPhone);

    void deleteByUser_EpicAccountId(String epicAccountId);
}
