package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.MemoResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.Channel;
import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import com.mtvs.devlinkbackend.channel.repository.ChannelRepository;
import com.mtvs.devlinkbackend.channel.repository.MemoRepository;
import com.mtvs.devlinkbackend.channel.repository.MemoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemoViewService {
    @Autowired
    private MemoViewRepository memoViewRepository;

    @Transactional(readOnly = true)
    public MemoResponseDTO getMemo(String channelId) {
        try {
            MemoResponseDTO memoResponseDTO = new MemoResponseDTO();
            List<MemoInfo> memoInfo = memoViewRepository.findAllByChannelId(channelId);
            memoResponseDTO.setMemoInfoList(memoInfo);
            return memoResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
