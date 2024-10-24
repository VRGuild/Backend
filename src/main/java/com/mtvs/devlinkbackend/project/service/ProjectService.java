package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.project.dto.response.ProjectPagingResponseDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectVectorRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSingleResponseDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    private static final int pageSize = 15;

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectSingleResponseDTO registProject(ProjectRegistRequestDTO projectRegistRequestDTO, String accountId) {
        return new ProjectSingleResponseDTO(projectRepository.save(new Project(
                projectRegistRequestDTO.getWorkScope(),
                projectRegistRequestDTO.getWorkType(),
                projectRegistRequestDTO.getProgressClassification(),
                projectRegistRequestDTO.getCompanyName(),
                projectRegistRequestDTO.getTitle(),
                projectRegistRequestDTO.getContent(),
                projectRegistRequestDTO.getRequiredClient(),
                projectRegistRequestDTO.getRequiredServer(),
                projectRegistRequestDTO.getRequiredDesign(),
                projectRegistRequestDTO.getRequiredPlanner(),
                projectRegistRequestDTO.getRequiredAIEngineer(),
                projectRegistRequestDTO.getStartDateTime(),
                projectRegistRequestDTO.getEndDateTime(),
                projectRegistRequestDTO.getEstimatedCost(),
                accountId
        )));
    }

    public ProjectSingleResponseDTO findProjectByProjectId(Long projectId) {
        return new ProjectSingleResponseDTO(projectRepository.findById(projectId).orElse(null));
    }

    public ProjectPagingResponseDTO findProjectsByAccountIdWithPaging(int page, String accountId) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsByAccountId(accountId, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsByWorkScopeWithPaging(int page, String workScope) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsByWorkScope(workScope, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsByWorkTypeWithPaging(int page, String workType) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsByWorkType(workType, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsByProgressClassificationWithPaging(
            int page,String progressClassification) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsByProgressClassification(progressClassification, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsByTitleContainingIgnoreCaseWithPaging(int page, String title) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsByTitleContainingIgnoreCase(title, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqualWithPaging(
            int page, LocalDate starDateTime, LocalDate endDateTime) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.
                findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(starDateTime, endDateTime, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public ProjectPagingResponseDTO findProjectsWithLargerRequirementsWithPaging(
            int page, Integer requiredClient, Integer requiredServer,
            Integer requiredDesign, Integer requiredPlanner, Integer requiredAIEngineer) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectRepository.findProjectsWithLargerRequirements(
                requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer, pageable);
        return new ProjectPagingResponseDTO(projectPage.getContent(), projectPage.getTotalPages());
    }

    public Map<String, Integer> findProjectVectorByProjectId(Long projectId) {
        return projectRepository.findById(projectId).orElse(null).getProjectVector();
    }

    @Transactional
    public ProjectSingleResponseDTO updateProject(ProjectUpdateRequestDTO projectUpdateRequestDTO, String accountId) {
        Optional<Project> request = projectRepository.findById(projectUpdateRequestDTO.getProjectId());
        if (request.isPresent()) {
            Project foundProject = request.get();
            if(foundProject.getAccountId().equals(accountId)) {
                foundProject.setWorkScope(projectUpdateRequestDTO.getWorkScope());
                foundProject.setWorkType(projectUpdateRequestDTO.getWorkType());
                foundProject.setProgressClassification(projectUpdateRequestDTO.getProgressClassification());
                foundProject.setTitle(projectUpdateRequestDTO.getTitle());
                foundProject.setContent(projectUpdateRequestDTO.getContent());
                foundProject.setRequiredClient(projectUpdateRequestDTO.getRequiredClient());
                foundProject.setRequiredServer(projectUpdateRequestDTO.getRequiredServer());
                foundProject.setRequiredDesign(projectUpdateRequestDTO.getRequiredDesign());
                foundProject.setRequiredPlanner(projectUpdateRequestDTO.getRequiredPlanner());
                foundProject.setRequiredAIEngineer(projectUpdateRequestDTO.getRequiredAIEngineer());
                foundProject.setStartDateTime(projectUpdateRequestDTO.getStartDateTime());
                foundProject.setEndDateTime(projectUpdateRequestDTO.getEndDateTime());
                foundProject.setEstimatedCost(projectUpdateRequestDTO.getEstimatedCost());
                return new ProjectSingleResponseDTO(foundProject);
            }
            else throw new IllegalArgumentException("잘못된 accountId로 Request ID : "
                    + projectUpdateRequestDTO.getProjectId() + "를 수정 시도");
        }
        else throw new IllegalArgumentException("잘못된 requestId로 수정 시도");
    }

    @Transactional
    public ProjectSingleResponseDTO updateProjectVector(ProjectVectorRegistRequestDTO projectVectorRegistRequestDTO) {
        Optional<Project> project = projectRepository.findById(projectVectorRegistRequestDTO.getProjectId());
        if(project.isPresent()) {
            Project foundProject = project.get();
            if(foundProject.getProjectVector().keySet().containsAll(List.of("x","y","z"))) {
                foundProject.setProjectVector(projectVectorRegistRequestDTO.getProjectVector());
                return new ProjectSingleResponseDTO(foundProject);
            } else
                throw new IllegalArgumentException("잘못된 vector key를 넣고 있음 | vector = " +
                        projectVectorRegistRequestDTO.getProjectVector());
        } else
            throw new IllegalArgumentException("등록되지 않은 Project에게 vector 정보를 추가/수정 시도 중");
    }

    public void deleteProject(Long requestId) {
        projectRepository.deleteById(requestId);
    }
}

