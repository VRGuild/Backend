package com.mtvs.devlinkbackend.experience.query.repository;

import com.mtvs.devlinkbackend.experience.command.model.entity.Experience;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceViewRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByUserId(Long userId, Pageable pageable);
}
