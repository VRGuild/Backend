package com.mtvs.devlinkbackend.request.repository;

import com.mtvs.devlinkbackend.request.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findRequestsByAccountId(String accountId);

    @Query("SELECT r FROM Request r WHERE r.startDateTime BETWEEN :startDateTime AND :endDateTime " +
            "AND r.endDateTime BETWEEN :startDateTime AND :endDateTime")
    List<Request> findRequestsWithinDateRange(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
