package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectSummaryRepository extends JpaRepository<Project, Long> {
    Page<ProjectSummary> findAllBy(Pageable pageable);
}
