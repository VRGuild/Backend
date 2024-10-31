package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.project.dto.response.*;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectIdAndContent;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectDetailService {

    private static final int pageSize = 15;

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

    public ProjectSummaryPagingResponseDTO findAllProjectSummaryWithPagination(int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<ProjectSummary> projectSummaryPage = projectRepository.findAllBy(pageable);
        List<ProjectPreviewDTO> projectPreviewDTOList = new ArrayList<>();

        for (ProjectSummary summary : projectSummaryPage.getContent()) {
            ProjectPreviewDTO previewDTO = new ProjectPreviewDTO();

            // workType 설정
            previewDTO.setWorkType(summary.getWorkType());
            previewDTO.setProgressClassification(summary.getProgressClassification());

            // previewTitle 생성: workType + (StartDateTime과 EndDateTime의 월 차이) + title
            long monthsDifference = ChronoUnit.MONTHS.between(summary.getStartDateTime(), summary.getEndDateTime());
            String previewTitle = "["+summary.getWorkType() + "] [" + monthsDifference + "개월";

            // requiredWorkers 설정
            List<String> requiredWorkers = new ArrayList<>();
            if (summary.getRequiredClient() != null && summary.getRequiredClient() > 0) {
                requiredWorkers.add("클라이언트");
                previewTitle = previewTitle + "/클라이언트(" + summary.getRequiredClient() + ")";
            }
            if (summary.getRequiredServer() != null && summary.getRequiredServer() > 0) {
                requiredWorkers.add("서버");
                previewTitle = previewTitle + "/서버(" + summary.getRequiredServer() + ")";
            }
            if (summary.getRequiredDesign() != null && summary.getRequiredDesign() > 0) {
                requiredWorkers.add("디자인");
                previewTitle = previewTitle + "/디자인(" + summary.getRequiredDesign() + ")";
            }
            if (summary.getRequiredPlanner() != null && summary.getRequiredPlanner() > 0) {
                requiredWorkers.add("기획");
                previewTitle = previewTitle + "/기획(" + summary.getRequiredPlanner() + ")";
            }
            if (summary.getRequiredAIEngineer() != null && summary.getRequiredAIEngineer() > 0) {
                requiredWorkers.add("AI");
                previewTitle = previewTitle + "/AI(" + summary.getRequiredAIEngineer() + ")";
            }
            previewTitle = previewTitle + "] " + summary.getTitle();

            previewDTO.setPreviewTitle(previewTitle);

            previewDTO.setRequiredWorkers(requiredWorkers);

            // deadlineDate 설정: createdAt에서 30일을 더한 날짜
            LocalDate deadlineDate = summary.getCreatedAt().toLocalDate().plusDays(30);
            previewDTO.setDeadlineDate(deadlineDate);

            // ProjectPreviewDTO 리스트에 추가
            projectPreviewDTOList.add(previewDTO);
        }

        return new ProjectSummaryPagingResponseDTO(projectPreviewDTOList, projectSummaryPage.getTotalPages(), projectRepository.count());
    }

    public ProjectSummaryPagingResponseDTO findAllProjectPreviewInHome() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<ProjectSummary> projectSummaryPage = projectRepository.findAllBy(pageable);
        List<ProjectPreviewDTO> projectPreviewDTOList = new ArrayList<>();

        for (ProjectSummary summary : projectSummaryPage.getContent()) {
            ProjectPreviewDTO previewDTO = new ProjectPreviewDTO();

            previewDTO.setProjectId(summary.getProjectId());

            // workType 설정
            previewDTO.setWorkType(summary.getWorkType());
            previewDTO.setProgressClassification(summary.getProgressClassification());

            // previewTitle 생성: workType + (StartDateTime과 EndDateTime의 월 차이) + title
            long monthsDifference = ChronoUnit.MONTHS.between(summary.getStartDateTime(), summary.getEndDateTime());
            String previewTitle = "[";
            if(summary.getWorkType().equals("both"))
                previewTitle += "상주, 원격";
            else if (summary.getWorkType().equals("local"))
                previewTitle += "상주";
            else if (summary.getWorkType().equals("remote"))
                previewTitle += "원격";
            previewTitle = previewTitle + "] [" + monthsDifference + "개월";

            // requiredWorkers 설정
            List<String> requiredWorkers = new ArrayList<>();
            if (summary.getRequiredClient() != null && summary.getRequiredClient() > 0) {
                requiredWorkers.add("클라이언트");
                previewTitle = previewTitle + "/클라이언트(" + summary.getRequiredClient() + ")";
            }
            if (summary.getRequiredServer() != null && summary.getRequiredServer() > 0) {
                requiredWorkers.add("서버");
                previewTitle = previewTitle + "/서버(" + summary.getRequiredServer() + ")";
            }
            if (summary.getRequiredDesign() != null && summary.getRequiredDesign() > 0) {
                requiredWorkers.add("디자인");
                previewTitle = previewTitle + "/디자인(" + summary.getRequiredDesign() + ")";
            }
            if (summary.getRequiredPlanner() != null && summary.getRequiredPlanner() > 0) {
                requiredWorkers.add("기획");
                previewTitle = previewTitle + "/기획(" + summary.getRequiredPlanner() + ")";
            }
            if (summary.getRequiredAIEngineer() != null && summary.getRequiredAIEngineer() > 0) {
                requiredWorkers.add("AI");
                previewTitle = previewTitle + "/AI(" + summary.getRequiredAIEngineer() + ")";
            }
            previewTitle = previewTitle + "] " + summary.getTitle();

            previewDTO.setPreviewTitle(previewTitle);

            previewDTO.setRequiredWorkers(requiredWorkers);

            // deadlineDate 설정: createdAt에서 30일을 더한 날짜
            LocalDate deadlineDate = summary.getCreatedAt().toLocalDate().plusDays(30);
            previewDTO.setDeadlineDate(deadlineDate);

            // ProjectPreviewDTO 리스트에 추가
            projectPreviewDTOList.add(previewDTO);
        }

        return new ProjectSummaryPagingResponseDTO(projectPreviewDTOList, projectSummaryPage.getTotalPages(), projectRepository.count());
    }

    public ProjectDetailResponseDTO findProjectDetailByProjectId(Long projectId) {
        ProjectIdAndContent projectIdAndContents = projectRepository.findProjectIdAndContentByProjectId(projectId);
        List<Comment> commentList = commentRepository.findCommentIdsByProjectId(projectId);
        List<Long> supportedTeamIdList = supportRepository.findTeamIdByProjectId(projectId);

        return new ProjectDetailResponseDTO(projectIdAndContents, commentList, supportedTeamIdList);
    }
}
