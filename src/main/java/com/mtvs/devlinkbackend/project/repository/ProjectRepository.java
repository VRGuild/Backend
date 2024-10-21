package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByAccountId(String accountId);

    List<Project> findProjectsByWorkScope(String workScope);

    List<Project> findProjectsByWorkType(String workType);

    List<Project> findProjectsByProgressClassification(String progressClassification);

    List<Project> findProjectsByTitleContainingIgnoreCase(String title);

    List<Project> findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);

    // required 값들이 넘겨준 값보다 큰 row를 조회하는 쿼리
    @Query("SELECT r FROM Request r WHERE " +
            "(:requiredClient IS NULL OR r.requiredClient > :requiredClient) AND " +
            "(:requiredServer IS NULL OR r.requiredServer > :requiredServer) AND " +
            "(:requiredDesign IS NULL OR r.requiredDesign > :requiredDesign) AND " +
            "(:requiredPlanner IS NULL OR r.requiredPlanner > :requiredPlanner) AND " +
            "(:requiredAIEngineer IS NULL OR r.requiredAIEngineer > :requiredAIEngineer)")
    List<Project> findProjectsWithLargerRequirements(
            @Param("requiredClient") Integer requiredClient,
            @Param("requiredServer") Integer requiredServer,
            @Param("requiredDesign") Integer requiredDesign,
            @Param("requiredPlanner") Integer requiredPlanner,
            @Param("requiredAIEngineer") Integer requiredAIEngineer
    );
}
