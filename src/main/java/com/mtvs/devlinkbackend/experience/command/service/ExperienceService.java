package com.mtvs.devlinkbackend.experience.command.service;

import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceRegistRequestDTO;
import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceUpdateRequestDTO;
import com.mtvs.devlinkbackend.experience.command.model.entity.Experience;
import com.mtvs.devlinkbackend.experience.command.repository.ExperienceRepository;
import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceSingleResponseDTO;
import com.mtvs.devlinkbackend.experience.query.repository.ExperienceViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceViewRepository experienceViewRepository;
    private final UserViewService userViewService;

    public ExperienceService(ExperienceRepository experienceRepository, ExperienceViewRepository experienceViewRepository, UserViewService userViewService) {
        this.experienceRepository = experienceRepository;
        this.experienceViewRepository = experienceViewRepository;
        this.userViewService = userViewService;
    }

    public ExperienceSingleResponseDTO registExperience(ExperienceRegistRequestDTO experienceRegistRequestDTO) {
        User user = userViewService.findUserByUserId(experienceRegistRequestDTO.getUserId()).getData();
        if(user == null)
            throw  new IllegalArgumentException("잘못된 UserId로 접근중");
        user.setExperienceValue(user.getExperienceValue() + experienceRegistRequestDTO.getAmount());

        return new ExperienceSingleResponseDTO(experienceRepository.save(new Experience(
                experienceRegistRequestDTO.getUserId(),
                experienceRegistRequestDTO.getCause(),
                experienceRegistRequestDTO.getAmount()
        )));
    }

    public ExperienceSingleResponseDTO updateExperience(ExperienceUpdateRequestDTO experienceUpdateRequestDTO) {
        Optional<Experience> experience = experienceViewRepository.findById(experienceUpdateRequestDTO.getExpId());
        if(experience.isEmpty())
            throw new IllegalArgumentException("잘못된 expId로 접근중");
        Experience foundExperience = experience.get();


        User user = userViewService.findUserByUserId(experienceUpdateRequestDTO.getUserId()).getData();
        if(user == null) {
            throw  new IllegalArgumentException("잘못된 UserId로 접근중");
        }

        Integer difference = experienceUpdateRequestDTO.getAmount() - foundExperience.getAmount();

        user.setExperienceValue(user.getExperienceValue() + difference);

        foundExperience.setAmount(experienceUpdateRequestDTO.getAmount());
        foundExperience.setCause(experienceUpdateRequestDTO.getCause());

        return new ExperienceSingleResponseDTO(foundExperience);
    }

    public void deleteExperienceByExpId(Long expId) {
        experienceRepository.deleteById(expId);
    }
}
