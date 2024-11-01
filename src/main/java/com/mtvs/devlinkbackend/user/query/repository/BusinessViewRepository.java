package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessViewRepository extends JpaRepository<Business, Long> {
    Business findBusinessByUser_EpicAccountId(String epicAccountId);

    List<Business> findBusinessByManagerNameContainingIgnoreCase(String managerName);

    List<Business> findByBusinessNameContainingIgnoreCase(String businessName);

    List<Business> findByManagerPhone(String managerPhone);
}
