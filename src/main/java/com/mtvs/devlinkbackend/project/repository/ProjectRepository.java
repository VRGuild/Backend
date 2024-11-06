package com.mtvs.devlinkbackend.project.repository;

import com.mtvs.devlinkbackend.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
