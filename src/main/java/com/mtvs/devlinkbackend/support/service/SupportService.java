package com.mtvs.devlinkbackend.support.service;

import com.mtvs.devlinkbackend.support.dto.SupportRegistRequestDTO;
import com.mtvs.devlinkbackend.support.entity.Support;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupportService {
    private final SupportRepository supportRepository;

    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Transactional
    public Support createSupport(SupportRegistRequestDTO supportRegistRequestDTO) {
        return supportRepository.save(new Support(
                supportRegistRequestDTO.getProjectId(),
                supportRegistRequestDTO.getTeamId(),
                "waiting"
        ));
    }

    public List<Support> findSupportsByProjectId(Long projectId) {
        return supportRepository.findSupportsByProjectId(projectId);
    }

    public List<Support> findSupportsByTeamId(Long teamId) {
        return supportRepository.findSupportsByTeamId(teamId);
    }

    public void deleteSupport(Long supportId) {
        supportRepository.deleteById(supportId);
    }
}
