package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.project.entity.ProjectSummary;
import com.mtvs.devlinkbackend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // AccountId로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByAccountId(String accountId, Pageable pageable);

    // WorkScope로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByWorkScope(String workScope, Pageable pageable);

    // WorkType으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByWorkType(String workType, Pageable pageable);

    // ProgressClassification으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByProgressClassification(String progressClassification, Pageable pageable);

    // Title에 특정 문자열이 포함된 프로젝트 조회 (대소문자 구분 없이, 페이징 추가)
    Page<Project> findProjectsByTitleContainingIgnoreCase(String title, Pageable pageable);

    // 시작일과 종료일 기준으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    // required 값들이 넘겨준 값보다 큰 row를 조회하는 쿼리 (페이징 추가)
    @Query("SELECT p FROM Project p WHERE " +
            "(:requiredClient IS NULL OR p.requiredClient > :requiredClient) AND " +
            "(:requiredServer IS NULL OR p.requiredServer > :requiredServer) AND " +
            "(:requiredDesign IS NULL OR p.requiredDesign > :requiredDesign) AND " +
            "(:requiredPlanner IS NULL OR p.requiredPlanner > :requiredPlanner) AND " +
            "(:requiredAIEngineer IS NULL OR p.requiredAIEngineer > :requiredAIEngineer)")
    Page<Project> findProjectsWithLargerRequirements(
            @Param("requiredClient") Integer requiredClient,
            @Param("requiredServer") Integer requiredServer,
            @Param("requiredDesign") Integer requiredDesign,
            @Param("requiredPlanner") Integer requiredPlanner,
            @Param("requiredAIEngineer") Integer requiredAIEngineer,
            Pageable pageable
    );
}
