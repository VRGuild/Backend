package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.project.dto.response.*;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndTeamIdListDTO;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectAndUserAndTeamIdListDTO;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectIdAndCommnetIdListDTO;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectIdAndTeamIdListDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectViewRepository;
import com.mtvs.devlinkbackend.support.service.SupportService;
import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserIdAndBusinessAndNicknameDTO;
import com.mtvs.devlinkbackend.user.query.service.EpicBusinessViewService;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectViewService {

    private static final int pageSize = 15;
    private final ProjectViewRepository projectViewRepository;
    private final SupportService supportService;
    private final UserViewService userViewService;
    private final EpicBusinessViewService epicBusinessViewService;

    public ProjectViewService(ProjectViewRepository projectViewRepository, SupportService supportService, UserViewService userViewService, EpicBusinessViewService epicBusinessViewService) {
        this.projectViewRepository = projectViewRepository;
        this.supportService = supportService;
        this.userViewService = userViewService;
        this.epicBusinessViewService = epicBusinessViewService;
    }

    public ProjectSingleResponseDTO findProjectByProjectId(Long projectId) {
        return new ProjectSingleResponseDTO(
                new ProjectAndTeamIdListDTO(
                        projectViewRepository.findById(projectId).orElse(null),
                        supportService.findTeamIdsByProjectId(projectId)
                )
        );
    }

    @Transactional
    public ProjectDetailSingleResponseDTO findProjectDetailByProjectId(Long projectId) {

        Project project = projectViewRepository.findById(projectId).orElse(null);
        if (project == null)
            throw new IllegalArgumentException("잘못된 ProjectId로 지원 시도");

        User user = userViewService.findUserByUserId(project.getUserId()).getData();

        Business business = epicBusinessViewService.findBusinessByBusinessId(user.getBusinessId()).getData();

        return new ProjectDetailSingleResponseDTO(
                new ProjectAndUserAndTeamIdListDTO(
                        project,
                        new UserIdAndBusinessAndNicknameDTO(
                                user.getUserId(),
                                business, user.getNickname()
                        ),
                        supportService.findTeamIdsByProjectId(projectId)
                )
        );
    }

    public ProjectPagingResponseDTO findProjectsWithPagination(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectViewRepository.findAllBy(pageable);

        List<ProjectAndTeamIdListDTO> projectAndTeamIdListDTOs =
                projectPage.getContent().stream().map(project -> {
                    List<Long> supportTeamIdList = supportService.findTeamIdsByProjectId(project.getProjectId());
                    return new ProjectAndTeamIdListDTO(project, supportTeamIdList);
                }).toList();

        return new ProjectPagingResponseDTO(
                projectAndTeamIdListDTOs,
                projectPage.getTotalPages(),
                projectViewRepository.count());
    }

    public ProjectDetailPagingResponseDTO findProjectDetailsWithPagination(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<Project> projectPage = projectViewRepository.findAllBy(pageable);

        List<ProjectAndUserAndTeamIdListDTO> projectDTOList = projectPage.stream()
                .map(project -> {
                    User user = userViewService.findUserByUserId(project.getUserId()).getData();

                    List<Long> supportTeamIdList = supportService.findTeamIdsByProjectId(project.getProjectId());

                    UserIdAndBusinessAndNicknameDTO userInfo = new UserIdAndBusinessAndNicknameDTO(
                            user.getUserId(),
                            user.getBusinessId() != null ?
                                    epicBusinessViewService.findBusinessByBusinessId(user.getBusinessId()).getData() :
                                    null,
                            user.getNickname()
                    );

                    return new ProjectAndUserAndTeamIdListDTO(project, userInfo, supportTeamIdList);
                }).toList();

        return new ProjectDetailPagingResponseDTO(
                projectDTOList,
                projectPage.getTotalPages(),
                projectPage.getTotalElements()
        );
    }

    public ProjectTeamResponseDTO findProjectTeamByProjectId(Long projectId) {
        List<Long> supportedTeamIdList = supportService.findTeamIdsByProjectId(projectId);
        return new ProjectTeamResponseDTO(new ProjectIdAndTeamIdListDTO(projectId, supportedTeamIdList));
    }

    public ProjectCommnetResponseDTO findProjectCommnetByProjectId(Long projectId) {
        Project project = projectViewRepository.findById(projectId).orElse(null);
        if (project == null)
            throw new IllegalArgumentException("잘못된 ProjectId로 호출중");
        return new ProjectCommnetResponseDTO(new ProjectIdAndCommnetIdListDTO(projectId, project.getCommentIdList()));
    }
}
