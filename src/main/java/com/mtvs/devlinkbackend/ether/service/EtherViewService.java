package com.mtvs.devlinkbackend.ether.service;

import com.mtvs.devlinkbackend.ether.dto.response.EtherPagingResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.EtherSingleResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.UserEtherAmountResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.sub.UserIdAndAmountDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.repository.EtherViewRepository;
import com.mtvs.devlinkbackend.ether.repository.projection.EtherGoldAndSilver;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtherViewService {

    private final static Integer PAGE_SIZE = 15;

    private final EtherViewRepository etherViewRepository;
    private final UserViewService userViewService;

    public EtherViewService(EtherViewRepository etherViewRepository, UserViewService userViewService) {
        this.etherViewRepository = etherViewRepository;
        this.userViewService = userViewService;
    }

    public EtherSingleResponseDTO findEtherByEtherId(Long etherId) {
        return new EtherSingleResponseDTO(etherViewRepository.findById(etherId).orElse(null));
    }

    public EtherPagingResponseDTO findEthersByAccountIdWithPagination(String accountId, Integer page) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 에픽 계정으로 접근 중");

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").ascending());
        Page<Ether> etherPage = etherViewRepository.findAllByUserId(user.getUserId(), pageable);
        return new EtherPagingResponseDTO(etherPage.getContent(), etherPage.getTotalPages(), etherViewRepository.count());
    }

    public UserEtherAmountResponseDTO findTotalEtherAmountByUserId(Long userId) {
        List<EtherGoldAndSilver> etherList = etherViewRepository.findEthersByUserId(userId);

        Integer totalGoldAmount = etherList.stream()
                .mapToInt(EtherGoldAndSilver::getGoldAmount)
                .sum();

        Integer totalSilverAmount = etherList.stream()
                .mapToInt(EtherGoldAndSilver::getSilverAmount)
                .sum();

        return new UserEtherAmountResponseDTO(new UserIdAndAmountDTO(userId, totalGoldAmount, totalSilverAmount));
    }
}
