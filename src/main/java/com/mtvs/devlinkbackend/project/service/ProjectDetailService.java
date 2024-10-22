package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryResponseDTO;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectIdAndContent;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDetailService {
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final SupportRepository supportRepository;

    public ProjectDetailService(ProjectRepository projectRepository, CommentRepository commentRepository, SupportRepository supportRepository) {
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
        this.supportRepository = supportRepository;
    }

    public ProjectSummaryResponseDTO findAllProjectSummary() {
        List<ProjectSummary> projectSummaryList = projectRepository.findAllBy();
        return new ProjectSummaryResponseDTO(projectSummaryList);
    }

    public ProjectDetailResponseDTO findProjectDetailByProjectId(Long projectId) {
        ProjectIdAndContent projectIdAndContents = projectRepository.findProjectIdAndContentByProjectId(projectId);
        List<Long> commentIdList = commentRepository.findCommentIdsByProjectId(projectId);
        List<Long> supportedTeamIdList = supportRepository.findTeamIdByProjectId(projectId);

        return new ProjectDetailResponseDTO(projectIdAndContents, commentIdList, supportedTeamIdList);
    }
}
