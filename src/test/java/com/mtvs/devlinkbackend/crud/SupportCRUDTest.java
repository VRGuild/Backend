package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.support.dto.request.SupportRegistRequestDTO;
import com.mtvs.devlinkbackend.support.dto.response.SupportListResponseDTO;
import com.mtvs.devlinkbackend.support.dto.response.SupportSingleResponseDTO;
import com.mtvs.devlinkbackend.support.entity.Support;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import com.mtvs.devlinkbackend.support.service.SupportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SupportCRUDTest {

    private Support support1;
    private Support support2;
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private SupportService supportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 미리 삽입할 Support 객체 생성
        support1 = new Support(1L, 2L, "waiting");
        support2 = new Support(1L, 3L, "accepted");

        // Mocking the repository calls
        supportRepository.save(support1);
        supportRepository.save(support2);
    }

    @Test
    public void testCreateSupport() {
        // given
        SupportRegistRequestDTO supportRegistRequestDTO = new SupportRegistRequestDTO(1L, 2L);

        // when
        SupportSingleResponseDTO createdSupport = supportService.createSupport(supportRegistRequestDTO);

        // then
        assertNotNull(createdSupport, "Created support should not be null");
        assertEquals(support1.getProjectId(), createdSupport.getData().getProjectId(), "Project ID should match");
        assertEquals(support1.getTeamId(), createdSupport.getData().getTeamId(), "Team ID should match");
        assertEquals("waiting", createdSupport.getData().getSupportConfirmation(), "Status should be 'waiting'");
    }

    @Test
    public void testFindSupportsByProjectId() {
        // when
        SupportListResponseDTO foundSupports = supportService.findSupportsByProjectId(1L);

        // then
        assertNotNull(foundSupports, "Found supports should not be null");
        assertEquals(2, foundSupports.getData().size(), "The size of found supports should be 2");
        assertEquals(support1.getProjectId(), foundSupports.getData().get(0).getProjectId(), "First support project ID should match");
        assertEquals(support2.getTeamId(), foundSupports.getData().get(1).getTeamId(), "Second support team ID should match");
    }

    @Test
    public void testFindSupportsByTeamId() {
        // when
        SupportListResponseDTO foundSupports = supportService.findSupportsByTeamId(2L);

        // then
        assertNotNull(foundSupports, "Found supports should not be null");
        assertEquals(1, foundSupports.getData().size(), "The size of found supports should be 1");
        assertEquals(support1.getTeamId(), foundSupports.getData().get(0).getTeamId(), "Support team ID should match");
        assertEquals(support1.getSupportConfirmation(), foundSupports.getData().get(0).getSupportConfirmation(), "Support status should match");
    }

    @Test
    public void testDeleteSupport() {
        // given
        Long supportId = support1.getSupportId();

        // when
        supportService.deleteSupport(supportId);
        SupportListResponseDTO foundSupports = supportService.findSupportsByProjectId(1L);
        System.out.println(foundSupports);

        // then
        assertEquals(1, foundSupports.getData().size());
    }
}
