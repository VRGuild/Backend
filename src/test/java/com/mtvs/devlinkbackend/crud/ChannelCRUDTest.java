package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.ChannelRegistRequestDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ChannelSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.repository.ChannelRepository;
import com.mtvs.devlinkbackend.channel.service.*;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ChannelCRUDTest {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserViewService userViewService;
    @Autowired
    private TileInfoService tileInfoService;
    @Autowired
    private ObjectInfoService objectInfoService;
    @Autowired
    private MemoService memoService;
    @Autowired
    private PdfService pdfService;

    @Test
    void testRegisterChannelInitInfo() {
        // Given
        String accountId = "testAccountId";
        ChannelRegistRequestDTO requestDTO = new ChannelRegistRequestDTO("TestChannel");

        User mockUser = new User(accountId);
        when(userViewService.findUserByEpicAccountId(accountId)).thenReturn(mockUser);

        // When
        ChannelSingleResponseDTO responseDTO = channelService.registerChannelInitInfo(requestDTO, accountId);

        // Then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getChannelName()).isEqualTo("TestChannel");

        Optional<Channel> savedChannel = channelRepository.findById(responseDTO.getData().getChannelId());
        assertThat(savedChannel).isPresent();
        assertThat(mockUser.getChannelList()).contains(savedChannel.get().getChannelId());
    }

    @Test
    void testUpdateChannelInitInfo() {
        // Given
        Channel channel = channelRepository.save(new Channel("OldChannel"));
        String channelId = channel.getChannelId();
        ChannelRegistRequestDTO requestDTO = new ChannelRegistRequestDTO("UpdatedChannel");

        // When
        ChannelSingleResponseDTO responseDTO = channelService.updateChannelInitInfo(requestDTO, channelId);

        // Then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getData().getChannelName()).isEqualTo("UpdatedChannel");

        Channel updatedChannel = channelRepository.findById(channelId).orElseThrow();
        assertThat(updatedChannel.getChannelName()).isEqualTo("UpdatedChannel");
    }

    @Test
    void testDeleteChannelByChannelId() {
        // Given
        Channel channel = channelRepository.save(new Channel("TestChannel"));
        String channelId = channel.getChannelId();

        // Mocking services
        doNothing().when(tileInfoService).deleteTileInfosByChannelId(channelId);
        doNothing().when(objectInfoService).deleteObjectInfosByChannelId(channelId);
        doNothing().when(memoService).deleteMemosByChannelId(channelId);
        doNothing().when(pdfService).deletePdfsByChannelId(channelId);

        // When
        channelService.deleteChannelByChannelId(channelId);

        // Then
        Optional<Channel> deletedChannel = channelRepository.findById(channelId);
        assertThat(deletedChannel).isNotPresent();

        // Verify that related services were called
        verify(tileInfoService, times(1)).deleteTileInfosByChannelId(channelId);
        verify(objectInfoService, times(1)).deleteObjectInfosByChannelId(channelId);
        verify(memoService, times(1)).deleteMemosByChannelId(channelId);
        verify(pdfService, times(1)).deletePdfsByChannelId(channelId);
    }
}
