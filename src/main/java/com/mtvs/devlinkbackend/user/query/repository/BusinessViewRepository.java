package com.mtvs.devlinkbackend.user.query.repository;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessViewRepository extends JpaRepository<Business, Long> {
    @Query("SELECT b.businessId FROM Business b WHERE b.userId = :userId")
    Long findBusinessIdByUserId(@Param("userId") Long userId);

    Business findBusinessByUserId(Long usreId);

    List<Business> findBusinessByManagerNameContainingIgnoreCase(String managerName);

    List<Business> findByBusinessNameContainingIgnoreCase(String businessName);

    List<Business> findByManagerPhone(String managerPhone);
}
