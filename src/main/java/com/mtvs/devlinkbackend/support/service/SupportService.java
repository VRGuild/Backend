package com.mtvs.devlinkbackend.support.service;

import com.mtvs.devlinkbackend.support.dto.response.SupportListResponseDTO;
import com.mtvs.devlinkbackend.support.dto.request.SupportRegistRequestDTO;
import com.mtvs.devlinkbackend.support.dto.response.SupportSingleResponseDTO;
import com.mtvs.devlinkbackend.support.entity.Support;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupportService {
    private final SupportRepository supportRepository;

    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Transactional
    public SupportSingleResponseDTO createSupport(SupportRegistRequestDTO supportRegistRequestDTO) {
        return new SupportSingleResponseDTO(supportRepository.save(new Support(
                supportRegistRequestDTO.getProjectId(),
                supportRegistRequestDTO.getTeamId(),
                "waiting"
        )));
    }

    public SupportListResponseDTO findSupportsByProjectId(Long projectId) {
        return new SupportListResponseDTO(supportRepository.findSupportsByProjectId(projectId));
    }

    public SupportListResponseDTO findSupportsByTeamId(Long teamId) {
        return new SupportListResponseDTO(supportRepository.findSupportsByTeamId(teamId));
    }

    public void deleteSupport(Long supportId) {
        supportRepository.deleteById(supportId);
    }
}
