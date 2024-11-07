package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.ether.dto.request.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.request.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.response.EtherSingleResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.UserEtherAmountResponseDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.repository.EtherRepository;
import com.mtvs.devlinkbackend.ether.repository.EtherViewRepository;
import com.mtvs.devlinkbackend.ether.repository.projection.EtherGoldAndSilver;
import com.mtvs.devlinkbackend.ether.service.EtherService;
import com.mtvs.devlinkbackend.ether.service.EtherViewService;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class EtherCRUDTest {
    @Autowired
    private EtherService etherService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EtherViewService etherViewService;
    @Autowired
    private EtherViewRepository etherViewRepository;
    @Autowired
    private EtherRepository etherRepository;

    @BeforeEach
    void setUp() {
        // Create a user if none exists
        if (userRepository.findAll().isEmpty()) {
            User user = new User();
            user.setNickname("TestUser");
            user.setExperienceValue(0);
            userRepository.save(user);
        }
    }

    @Test
    @DisplayName("Ether 등록 서비스 테스트")
    public void registEtherTest() {
        List<User> user = userRepository.findAll();

        EtherRegistRequestDTO requestDTO = new EtherRegistRequestDTO(user.get(0).getUserId(), 100, 200, "test cause");

        EtherSingleResponseDTO responseDTO = etherService.registEther(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getGoldAmount()).isEqualTo(100);
        assertThat(responseDTO.getData().getSilverAmount()).isEqualTo(200);
    }

    @Test
    @DisplayName("Ether 수정 서비스 테스트")
    public void updateEtherTest() {
        List<User> user = userRepository.findAll();

        EtherRegistRequestDTO registRequestDTO = new EtherRegistRequestDTO(user.get(0).getUserId(), 100, 200, "test cause");

        EtherSingleResponseDTO registResponseDTO = etherService.registEther(registRequestDTO);

        EtherUpdateRequestDTO requestDTO = new EtherUpdateRequestDTO(registResponseDTO.getData().getEtherId(), user.get(0).getUserId(), 150, 250, "updated cause");

        EtherSingleResponseDTO responseDTO = etherService.updateEther(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getCause()).isEqualTo("updated cause");
        assertThat(responseDTO.getData().getGoldAmount()).isEqualTo(150);
        assertThat(responseDTO.getData().getSilverAmount()).isEqualTo(250);
    }

    @Test
    @DisplayName("존재하지 않는 Ether 수정 서비스 테스트")
    public void updateEtherNotFoundTest() {
        List<User> user = userRepository.findAll();

        EtherUpdateRequestDTO requestDTO = new EtherUpdateRequestDTO(1L, user.get(0).getUserId(), 150, 250, "updated cause");

        Assertions.assertThrows(IllegalArgumentException.class, () -> etherService.updateEther(requestDTO));
    }

    @Test
    @DisplayName("Ether 삭제 서비스 테스트")
    public void deleteEtherByEtherIdTest() {
        List<User> user = userRepository.findAll();

        EtherRegistRequestDTO requestDTO = new EtherRegistRequestDTO(user.get(0).getUserId(), 100, 200, "test cause");

        Ether ether = etherService.registEther(requestDTO).getData();

        assertDoesNotThrow(() -> etherService.deleteEtherByEtherId(ether.getEtherId()));
    }

    @Test
    @DisplayName("유저 ID로 Ether 총 금액 조회 테스트")
    public void findTotalEtherAmountByUserIdTest() {
        List<User> userList = userRepository.findAll();
        Long userId = userList.get(0).getUserId();

        Ether ether1 = new Ether(userId, "cause 1", 100, 200);
        Ether ether2 = new Ether(userId, "cause 2", 150, 250);
        etherRepository.save(ether1);
        etherRepository.save(ether2);

        assertDoesNotThrow(() -> {
            UserEtherAmountResponseDTO responseDTO = etherViewService.findTotalEtherAmountByUserId(userId);
            assertThat(responseDTO).isNotNull();
            assertThat(responseDTO.getData().getGoldAmount()).isEqualTo(250);
            assertThat(responseDTO.getData().getSilverAmount()).isEqualTo(450);
        });
    }
}
