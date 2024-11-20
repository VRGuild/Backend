package com.mtvs.devlinkbackend.project.service;

import com.mtvs.devlinkbackend.common.model.OccupationType;
import com.mtvs.devlinkbackend.project.dto.response.*;
import com.mtvs.devlinkbackend.project.dto.response.sub.ProjectPreviewDTO;
import com.mtvs.devlinkbackend.project.entity.Occupation;
import com.mtvs.devlinkbackend.project.repository.ProjectSummaryRepository;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectSummaryViewService {

    private static final int pageSize = 15;

    private final ProjectRepository projectRepository;
    private final ProjectSummaryRepository projectSummaryRepository;

    public ProjectSummaryViewService(ProjectRepository projectRepository, ProjectSummaryRepository projectSummaryRepository) {
        this.projectRepository = projectRepository;
        this.projectSummaryRepository = projectSummaryRepository;
    }

    public ProjectSummaryPagingResponseDTO findAllProjectSummaryWithPagination(int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<ProjectSummary> projectSummaryPage = projectSummaryRepository.findAllBy(pageable);
        List<ProjectPreviewDTO> projectPreviewDTOList = projectSummaryPage.stream().map(projectSummary -> {
            ProjectPreviewDTO previewDTO = new ProjectPreviewDTO();

            // workType 설정
            previewDTO.setWorkType(projectSummary.getWorkType());
            previewDTO.setProgressClassification(projectSummary.getProgressClassification());
            previewDTO.setProjectId(projectSummary.getProjectId());

            // previewTitle 생성: workType + (StartDateTime과 EndDateTime의 월 차이) + title
            long monthsDifference = ChronoUnit.MONTHS.between(
                    projectSummary.getStartDate(), projectSummary.getEndDate());

            StringBuilder previewTitle = new StringBuilder("[");
            if(projectSummary.getWorkType().equals("both"))
                previewTitle.append("상주, 원격");
            else if (projectSummary.getWorkType().equals("local"))
                previewTitle.append("상주");
            else if (projectSummary.getWorkType().equals("remote"))
                previewTitle.append("원격");
            previewTitle.append("] [").append(monthsDifference).append("개월");

            List<String> requiredOccupationNameList = new ArrayList<>();
            for (Occupation occupation : projectSummary.getRequiredOccupationList()) {
                OccupationType occupationType = Arrays.stream(OccupationType.values())
                        .filter(type -> type.getFieldName().equalsIgnoreCase(occupation.getOccupationName()))
                        .findFirst()
                        .orElse(null);

                if (occupationType != null) {
                    // Add the display name to the required occupation name list
                    requiredOccupationNameList.add(occupationType.getDisplayName());

                    // Append to the preview title
                    previewTitle.append("/").append(occupationType.getDisplayName())
                            .append("(").append(occupation.getOccupationCount()).append(")");
                }
            }

            // deadlineDate 설정: createdAt에서 30일을 더한 날짜
            LocalDate deadlineDate = projectSummary.getCreatedAt().toLocalDate().plusDays(30);
            previewDTO.setDeadlineDate(deadlineDate);

            previewTitle.append("] ").append(projectSummary.getTitle());

            previewDTO.setPreviewTitle(previewTitle.toString());

            previewDTO.setRequiredOccupationNameList(requiredOccupationNameList);

            // ProjectPreviewDTO 리스트에 추가
            return previewDTO;
        }).toList();

        return new ProjectSummaryPagingResponseDTO(
                projectPreviewDTOList,
                projectSummaryPage.getTotalPages(),
                projectSummaryRepository.count()
        );
    }

    public ProjectSummaryPagingResponseDTO findAllProjectPreviewInHome() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<ProjectSummary> projectSummaryPage = projectSummaryRepository.findAllBy(pageable);
        List<ProjectPreviewDTO> projectPreviewDTOList = projectSummaryPage.stream().map(projectSummary -> {
            ProjectPreviewDTO previewDTO = new ProjectPreviewDTO();

            // workType 설정
            previewDTO.setProjectId(projectSummary.getProjectId());
            previewDTO.setWorkType(projectSummary.getWorkType());
            previewDTO.setProgressClassification(projectSummary.getProgressClassification());

            // previewTitle 생성: workType + (StartDateTime과 EndDateTime의 월 차이) + title
            long monthsDifference = ChronoUnit.MONTHS.between(
                    projectSummary.getStartDate(), projectSummary.getEndDate());

            StringBuilder previewTitle = new StringBuilder("[");
            if(projectSummary.getWorkType().equals("both"))
                previewTitle.append("상주, 원격");
            else if (projectSummary.getWorkType().equals("local"))
                previewTitle.append("상주");
            else if (projectSummary.getWorkType().equals("remote"))
                previewTitle.append("원격");
            previewTitle.append("] [").append(monthsDifference).append("개월");

            List<String> requiredOccupationNameList = new ArrayList<>();
            for (Occupation occupation : projectSummary.getRequiredOccupationList()) {
                OccupationType occupationType = Arrays.stream(OccupationType.values())
                        .filter(type -> type.getFieldName().equalsIgnoreCase(occupation.getOccupationName()))
                        .findFirst()
                        .orElse(null);

                if (occupationType != null) {
                    // Add the display name to the required occupation name list
                    requiredOccupationNameList.add(occupationType.getDisplayName());

                    // Append to the preview title
                    previewTitle.append("/").append(occupationType.getDisplayName())
                            .append("(").append(occupation.getOccupationCount()).append(")");
                }
            }

            // deadlineDate 설정: createdAt에서 30일을 더한 날짜
            LocalDate deadlineDate = projectSummary.getCreatedAt().toLocalDate().plusDays(30);
            previewDTO.setDeadlineDate(deadlineDate);

            previewTitle.append("] ").append(projectSummary.getTitle());

            previewDTO.setPreviewTitle(previewTitle.toString());

            previewDTO.setRequiredOccupationNameList(requiredOccupationNameList);

            // ProjectPreviewDTO 리스트에 추가
            return previewDTO;
        }).toList();

        return new ProjectSummaryPagingResponseDTO(projectPreviewDTOList, projectSummaryPage.getTotalPages(), projectRepository.count());
    }
}
