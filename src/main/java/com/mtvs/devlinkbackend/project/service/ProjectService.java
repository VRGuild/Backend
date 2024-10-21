package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.project.dto.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.dto.ProjectVectorRegistRequestDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project registProject(ProjectRegistRequestDTO projectRegistRequestDTO, String accountId) {
        return projectRepository.save(new Project(
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
        ));
    }

    public Project findProjectByProjectId(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public List<Project> findProjectsByAccountId(String accountId) {
        return projectRepository.findProjectsByAccountId(accountId);
    }

    public List<Project> findProjectsByWorkScope(String workScope) {
        return projectRepository.findProjectsByWorkScope(workScope);
    }

    public List<Project> findProjectsByWorkType(String workType) {
        return projectRepository.findProjectsByWorkType(workType);
    }

    public List<Project> findProjectsByProgressClassification(String progressClassification) {
        return projectRepository.findProjectsByProgressClassification(progressClassification);
    }

    public List<Project> findProjectsByTitleContainingIgnoreCase(String title) {
        return projectRepository.findProjectsByTitleContainingIgnoreCase(title);
    }

    public List<Project> findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(
            LocalDateTime starDateTime, LocalDateTime endDateTime) {
        return projectRepository.findProjectsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(starDateTime, endDateTime);
    }

    public List<Project> findProjectsWithLargerRequirements(
            Integer requiredClient, Integer requiredServer, Integer requiredDesign,
            Integer requiredPlanner, Integer requiredAIEngineer) {

        return projectRepository.findProjectsWithLargerRequirements(
                requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer);
    }

    public Map<String, Integer> findProjectVectorByProjectId(Long projectId) {
        return projectRepository.findById(projectId).orElse(null).getProjectVector();
    }

    @Transactional
    public Project updateProject(ProjectUpdateRequestDTO projectUpdateRequestDTO, String accountId) {
        Optional<Project> request = projectRepository.findById(projectUpdateRequestDTO.getRequestId());
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
                return foundProject;
            }
            else throw new IllegalArgumentException("잘못된 accountId로 Request ID : "
                    + projectUpdateRequestDTO.getRequestId() + "를 수정 시도");
        }
        else throw new IllegalArgumentException("잘못된 requestId로 수정 시도");
    }

    @Transactional
    public Project updateProjectVector(ProjectVectorRegistRequestDTO projectVectorRegistRequestDTO) {
        Optional<Project> project = projectRepository.findById(projectVectorRegistRequestDTO.getProjectId());
        if(project.isPresent()) {
            Project foundProject = project.get();
            if(foundProject.getProjectVector().keySet().containsAll(List.of("x","y","z"))) {
                foundProject.setProjectVector(projectVectorRegistRequestDTO.getProjectVector());
                return foundProject;
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

