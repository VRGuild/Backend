package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.project.dto.response.ProjectTeamResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndTeamIdListDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectIdAndTeamIdListDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import com.mtvs.devlinkbackend.project.repository.ProjectViewRepository;
import com.mtvs.devlinkbackend.support.dto.request.SupportRegistRequestDTO;
import com.mtvs.devlinkbackend.support.service.SupportService;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.service.TeamService;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SupportService supportService;
    private final ProjectViewRepository projectViewRepository;
    private final TeamService teamService;
    private final UserViewService userViewService;

    public ProjectService(ProjectRepository projectRepository, SupportService supportService, ProjectViewRepository projectViewRepository, TeamService teamService, UserViewService userViewService) {
        this.projectRepository = projectRepository;
        this.supportService = supportService;
        this.projectViewRepository = projectViewRepository;
        this.teamService = teamService;
        this.userViewService = userViewService;
    }

    @Transactional
    public ProjectSingleResponseDTO registProject(ProjectRegistRequestDTO projectRegistRequestDTO, String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 userId 매핑");

        Project project = projectRepository.save(new Project(
                user.getUserId(),
                projectRegistRequestDTO.getTitle(),
                projectRegistRequestDTO.getContent(),
                projectRegistRequestDTO.getWorkType(),
                projectRegistRequestDTO.getProgressClassification(),
                projectRegistRequestDTO.getRequiredOccupationList(),
                projectRegistRequestDTO.getStartDate(),
                projectRegistRequestDTO.getEndDate(),
                projectRegistRequestDTO.getEstimatedCost()
        ));
        List<Long> supportTeamIdList = new ArrayList<>();

        return new ProjectSingleResponseDTO(new ProjectAndTeamIdListDTO(project, supportTeamIdList));
    }

    @Transactional
    public ProjectSingleResponseDTO updateProject(ProjectUpdateRequestDTO projectUpdateRequestDTO) {
        Optional<Project> request = projectViewRepository.findById(projectUpdateRequestDTO.getProjectId());
        if (request.isPresent()) {
            Project foundProject = request.get();
            if(foundProject.getUserId().equals(projectUpdateRequestDTO.getUserId())) {
                foundProject.setWorkType(projectUpdateRequestDTO.getWorkType());
                foundProject.setProgressClassification(projectUpdateRequestDTO.getProgressClassification());
                foundProject.setTitle(projectUpdateRequestDTO.getTitle());
                foundProject.setContent(projectUpdateRequestDTO.getContent());
                foundProject.setRequiredOccupationList(projectUpdateRequestDTO.getRequiredOccupationList());
                foundProject.setStartDate(projectUpdateRequestDTO.getStartDate());
                foundProject.setEndDate(projectUpdateRequestDTO.getEndDate());
                foundProject.setEstimatedCost(projectUpdateRequestDTO.getEstimatedCost());

                return new ProjectSingleResponseDTO(
                        new ProjectAndTeamIdListDTO(
                                foundProject,
                                supportService.findTeamIdsByProjectId(foundProject.getProjectId()))
                );
            }
            else throw new IllegalArgumentException("잘못된 accountId로 Request ID : "
                    + projectUpdateRequestDTO.getProjectId() + "를 수정 시도");
        }
        else throw new IllegalArgumentException("잘못된 requestId로 수정 시도");
    }

    @Transactional
    public ProjectTeamResponseDTO applyProjectByNewTeam(TeamRegistRequestDTO teamRegistRequestDTO, Long projectId) {
        Team newTeam = teamService.registTeam(teamRegistRequestDTO).getData();

        supportService.createSupport(new SupportRegistRequestDTO(projectId, newTeam.getTeamId()));

        return new ProjectTeamResponseDTO(
                new ProjectIdAndTeamIdListDTO(
                        projectId,
                        supportService.findTeamIdsByProjectId(projectId)));
    }

    @Transactional
    public boolean acceptTeam(Long userId, Long projectId, Long teamId) {
        try{
            projectRepository.updateSupportConfirmation(projectId, teamId, userId, AcceptStatus.ACCEPTED);
            return true;
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }


}

