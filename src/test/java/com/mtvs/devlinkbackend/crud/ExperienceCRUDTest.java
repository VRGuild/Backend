package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceRegistRequestDTO;
import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceUpdateRequestDTO;
import com.mtvs.devlinkbackend.experience.command.service.ExperienceService;
import com.mtvs.devlinkbackend.experience.query.service.ExperienceViewService;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ExperienceCRUDTest {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ExperienceViewService experienceViewService;

    @Autowired
    private UserViewService userViewService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Create a user if none exists
        if (userRepository.findAll().isEmpty()) {
            User user = new User();
            user.setNickname("TestUser");
            user.setExperienceValue(0);
            user = userRepository.save(user);
        }

        List<User> user = userRepository.findAll();
    }

    @Test
    @DisplayName("Experience 등록 테스트")
    void registExperienceTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceRegistRequestDTO requestDTO = new ExperienceRegistRequestDTO(1L, user.getUserId(), "Test Cause", 100);
        var responseDTO = experienceService.registExperience(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getCause()).isEqualTo("Test Cause");
        user = userViewService.findUserByUserId(user.getUserId()).getData();
        assertThat(user.getExperienceValue()).isEqualTo(100);
    }

    @Test
    @DisplayName("잘못된 UserId로 Experience 등록 시도 테스트")
    void registExperienceWithInvalidUserIdTest() {
        ExperienceRegistRequestDTO requestDTO = new ExperienceRegistRequestDTO(1L,999L, "Test Cause", 100);
        assertThatThrownBy(() -> experienceService.registExperience(requestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 UserId로 접근중");
    }

    @Test
    @DisplayName("Experience 수정 테스트")
    void updateExperienceTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceRegistRequestDTO registRequestDTO = new ExperienceRegistRequestDTO(1L, user.getUserId(), "Initial Cause", 100);
        var registResponseDTO = experienceService.registExperience(registRequestDTO);

        ExperienceUpdateRequestDTO updateRequestDTO = new ExperienceUpdateRequestDTO(
                registResponseDTO.getData().getExpId(),
                user.getUserId(),
                "Updated Cause",
                200
        );

        var updateResponseDTO = experienceService.updateExperience(updateRequestDTO);
        assertThat(updateResponseDTO).isNotNull();
        assertThat(updateResponseDTO.getData().getCause()).isEqualTo("Updated Cause");
        assertThat(updateResponseDTO.getData().getAmount()).isEqualTo(200);
    }

    @Test
    @DisplayName("잘못된 ExperienceId로 Experience 수정 시도 테스트")
    void updateExperienceWithInvalidExpIdTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceUpdateRequestDTO updateRequestDTO = new ExperienceUpdateRequestDTO(999L, user.getUserId(), "Updated Cause", 200);
        assertThatThrownBy(() -> experienceService.updateExperience(updateRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 expId로 접근중");
    }

    @Test
    @DisplayName("잘못된 UserId로 Experience 수정 시도 테스트")
    void updateExperienceWithInvalidUserIdTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceRegistRequestDTO registRequestDTO = new ExperienceRegistRequestDTO(1L,user.getUserId(), "Initial Cause", 100);
        var registResponseDTO = experienceService.registExperience(registRequestDTO);

        ExperienceUpdateRequestDTO updateRequestDTO = new ExperienceUpdateRequestDTO(
                registResponseDTO.getData().getExpId(),
                999L,  // 잘못된 UserId
                "Updated Cause",
                200
        );

        assertThatThrownBy(() -> experienceService.updateExperience(updateRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 UserId로 접근중");
    }

    @Test
    @DisplayName("Experience 삭제 테스트")
    void deleteExperienceTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceRegistRequestDTO requestDTO = new ExperienceRegistRequestDTO(1L,user.getUserId(), "Test Cause", 100);
        var responseDTO = experienceService.registExperience(requestDTO);

        Long expId = 999L;
        experienceService.deleteExperienceByExpId(expId);

        Assertions.assertNull(experienceViewService.findExperienceByExpId(expId).getData());
    }

    @Test
    @DisplayName("UserId로 Experience 목록 조회 테스트")
    void findExperiencesByUserIdTest() {
        User user = userRepository.findAll().get(0);
        assertThat(user).isNotNull();

        ExperienceRegistRequestDTO requestDTO1 = new ExperienceRegistRequestDTO(1L, user.getUserId(), "Cause 1", 50);
        ExperienceRegistRequestDTO requestDTO2 = new ExperienceRegistRequestDTO(2L, user.getUserId(), "Cause 2", 150);
        experienceService.registExperience(requestDTO1);
        experienceService.registExperience(requestDTO2);

        var experienceListDTO = experienceViewService.findExperiencesByUserId(user.getUserId(), 0);
        assertThat(experienceListDTO).isNotNull();
        assertThat(experienceListDTO.getData()).hasSize(2);
    }
}
