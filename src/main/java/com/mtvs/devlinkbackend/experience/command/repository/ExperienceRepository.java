package com.mtvs.devlinkbackend.experience.command.repository;

import com.mtvs.devlinkbackend.experience.command.model.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
