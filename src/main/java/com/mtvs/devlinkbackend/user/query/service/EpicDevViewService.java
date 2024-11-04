package com.mtvs.devlinkbackend.user.query.service;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.model.dto.response.DevPagingResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.DevSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.repository.DevViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EpicDevViewService {
    private final DevViewRepository devViewRepository;
    private final UserViewRepository userViewRepository;

    private static final Integer PAGE_SIZE = 15;

    public EpicDevViewService(DevViewRepository devViewRepository, UserViewRepository userViewRepository) {
        this.devViewRepository = devViewRepository;
        this.userViewRepository = userViewRepository;
    }

    public DevSingleResponseDTO findDevByEpicAccountId(String accountId) throws Exception {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        if(foundUser == null)
            throw new IllegalArgumentException("저장된 유저 정보가 없음");
        return new DevSingleResponseDTO(devViewRepository.findDevByUserId(foundUser.getUserId()));
    }

    public DevSingleResponseDTO findDevByUserId(Long userId) {
        return new DevSingleResponseDTO(devViewRepository.findDevByUserId(userId));
    }

    public DevPagingResponseDTO findAllDevsWithPagination(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
        Page<Dev> devPage = devViewRepository.findAllBy(pageable);
        return new DevPagingResponseDTO(devPage.getContent(), devPage.getTotalPages());
    }
}
