package com.mtvs.devlinkbackend.crud;


import com.mtvs.devlinkbackend.project.dto.request.ProjectRegistRequestDTO;
import com.mtvs.devlinkbackend.project.dto.request.ProjectUpdateRequestDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSingleResponseDTO;
import com.mtvs.devlinkbackend.project.dto.response.ProjectSummaryPagingResponseDTO;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import com.mtvs.devlinkbackend.project.repository.ProjectSummaryRepository;
import com.mtvs.devlinkbackend.project.repository.ProjectViewRepository;
import com.mtvs.devlinkbackend.project.repository.projection.ProjectSummary;
import com.mtvs.devlinkbackend.project.service.ProjectService;
import com.mtvs.devlinkbackend.project.service.ProjectSummaryViewService;
import com.mtvs.devlinkbackend.project.service.ProjectViewService;
import com.mtvs.devlinkbackend.support.service.SupportService;
import com.mtvs.devlinkbackend.team.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ProjectCRUDTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectViewRepository projectViewRepository;

    @InjectMocks
    private ProjectService projectService;
    @Autowired
    private ProjectSummaryRepository projectSummaryRepository;
    @Autowired
    private ProjectSummaryViewService projectSummaryViewService;
    @Autowired
    private ProjectViewService projectViewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("프로젝트 등록 테스트")
    void registProjectTest() {
        // Given
        ProjectRegistRequestDTO requestDTO = new ProjectRegistRequestDTO(1L, "Test Title", "Test Content", 1000, "in-progress", "both", null, null, null);
        Project project = new Project(1L, "Test Title", "Test Content", "both", "in-progress", null, null, null, 1000);

        when(projectRepository.save(ArgumentMatchers.any(Project.class))).thenReturn(project);

        // When
        ProjectSingleResponseDTO response = projectService.registProject(requestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getProjectInfo()).isEqualTo(project);
    }

    @Test
    @DisplayName("프로젝트 업데이트 테스트")
    void updateProjectTest() {
        // Given
        ProjectUpdateRequestDTO requestDTO = new ProjectUpdateRequestDTO(
                1L,
                1L,
                "Updated Title",
                "Updated Content",
                2000,
                "new",
                "remote",
                null,
                null,
                LocalDate.now(),
                LocalDate.now()
        );
        Project project = new Project(1L, "Old Title", "Old Content", "both", "in-progress", null, null, null, 1000);

        when(projectViewRepository.findById(1L)).thenReturn(Optional.of(project));

        // When
        ProjectSingleResponseDTO response = projectService.updateProject(requestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getProjectInfo().getTitle()).isEqualTo("Updated Title");
        assertThat(response.getData().getProjectInfo().getEstimatedCost()).isEqualTo(2000);
    }

    @Test
    @DisplayName("프로젝트 삭제 테스트")
    void deleteProjectTest() {
        // Given
        Long projectId = 1L;

        // When
        projectService.deleteProject(projectId);

        // Then
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    @DisplayName("프로젝트 목록 페이지네이션 조회 테스트")
    void findAllProjectSummaryWithPaginationTest() {
        // Given
        ProjectSummary projectSummary = mock(ProjectSummary.class);
        Page<ProjectSummary> page = new PageImpl<>(Collections.singletonList(projectSummary));

        when(projectSummaryRepository.findAllBy(any(Pageable.class))).thenReturn(page);
        when(projectSummaryRepository.count()).thenReturn(1L);

        // When
        ProjectSummaryPagingResponseDTO response = projectSummaryViewService.findAllProjectSummaryWithPagination(0);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getPreviewDTOList()).hasSize(1);
    }

    @Test
    @DisplayName("홈 프로젝트 목록 조회 테스트")
    void findAllProjectPreviewInHomeTest() {
        // Given
        ProjectSummary projectSummary = mock(ProjectSummary.class);
        Page<ProjectSummary> page = new PageImpl<>(Collections.singletonList(projectSummary));

        when(projectSummaryRepository.findAllBy(any(Pageable.class))).thenReturn(page);

        // When
        ProjectSummaryPagingResponseDTO response = projectSummaryViewService.findAllProjectPreviewInHome();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getPreviewDTOList()).hasSize(1);
    }

    @Test
    @DisplayName("프로젝트 단일 조회 테스트")
    void findProjectByProjectIdTest() {
        // Given
        Project project = mock(Project.class);
        when(projectViewRepository.findById(1L)).thenReturn(Optional.of(project));

        // When
        ProjectSingleResponseDTO response = projectViewService.findProjectByProjectId(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getProjectInfo()).isEqualTo(project);
    }

    @Test
    @DisplayName("프로젝트 상세 조회 테스트")
    void findProjectDetailByProjectIdTest() {
        // Given
        Project project = mock(Project.class);
        when(projectViewRepository.findById(1L)).thenReturn(Optional.of(project));

        // When
        ProjectDetailSingleResponseDTO response = projectViewService.findProjectDetailByProjectId(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getData().getProjectInfo()).isEqualTo(project);
    }
}
