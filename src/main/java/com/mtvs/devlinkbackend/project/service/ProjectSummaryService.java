package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryResponseDTO;
import com.mtvs.devlinkbackend.project.entity.ProjectSummary;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectSummaryService {
    private final ProjectRepository projectRepository;

    public ProjectSummaryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectSummaryResponseDTO findAllWithoutCommentsAndContent() {
        List<ProjectSummary> projectSummaryList = projectRepository.findAll()
                .stream()
                .map(ProjectSummary::new)  // Project 객체를 ProjectSummary로 변환
                .collect(Collectors.toList());  // 리스트로 수집

        return new ProjectSummaryResponseDTO(projectSummaryList);
    }
}
