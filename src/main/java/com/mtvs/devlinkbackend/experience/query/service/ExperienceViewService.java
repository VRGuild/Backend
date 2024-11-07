package com.mtvs.devlinkbackend.experience.query.service;

import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceListResponseDTO;
import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceSingleResponseDTO;
import com.mtvs.devlinkbackend.experience.query.repository.ExperienceViewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExperienceViewService {

    private final static Integer PAGE_SIZE = 15;

    private final ExperienceViewRepository experienceViewRepository;

    public ExperienceViewService(ExperienceViewRepository experienceViewRepository) {
        this.experienceViewRepository = experienceViewRepository;
    }

    public ExperienceSingleResponseDTO findExperienceByExpId(Long expId) {
        return new ExperienceSingleResponseDTO(experienceViewRepository.findById(expId).orElse(null));
    }

    public ExperienceListResponseDTO findExperiencesByUserId(Long userId, Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").ascending());
        return new ExperienceListResponseDTO(experienceViewRepository.findAllByUserId(userId, pageable));
    }
}
