package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectIdAndContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProjectViewRepository extends JpaRepository<Project, Long> {
    // AccountId로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByUserId(Long userId, Pageable pageable);

    // WorkType으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByWorkType(String workType, Pageable pageable);

    // ProgressClassification으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByProgressClassification(String progressClassification, Pageable pageable);

    // Title에 특정 문자열이 포함된 프로젝트 조회 (대소문자 구분 없이, 페이징 추가)
    Page<Project> findProjectsByTitleContainingIgnoreCase(String title, Pageable pageable);

    // 시작일과 종료일 기준으로 프로젝트 조회 (페이징 추가)
    Page<Project> findProjectsByStartDateLessThanEqualOrEndDateGreaterThanEqual(
            LocalDate startDate, LocalDate endDate, Pageable pageable);


    ProjectIdAndContent findProjectIdAndContentByProjectId(Long projectId);

    Page<Project> findAllBy(Pageable pageable);

    @Query("SELECT p.userId FROM Project p WHERE p.projectId = :projectId")
    Long findUserIdByProjectId(@Param("projectId") Long projectId);
}
