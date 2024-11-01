package com.mtvs.devlinkbackend.user.command.service;

import com.mtvs.devlinkbackend.user.command.model.dto.request.DevRequestDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserPartnerSingleResponseDTO;
import com.mtvs.devlinkbackend.user.command.model.entity.CategoryInfo;
import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.DevRepository;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.repository.DevViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EpicDevService {
    private final DevRepository devRepository;
    private final UserRepository userRepository;
    private final UserViewRepository userViewRepository;
    private final DevViewRepository devViewRepository;

    public EpicDevService(DevRepository devRepository, UserRepository userRepository, UserViewRepository userViewRepository, DevViewRepository devViewRepository) {
        this.devRepository = devRepository;
        this.userRepository = userRepository;
        this.userViewRepository = userViewRepository;
        this.devViewRepository = devViewRepository;
    }

    @Transactional
    public UserPartnerSingleResponseDTO registUserPartner(DevRequestDTO devRequestDTO,
                                                          String accountId) {
        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null) {
            user = new User(
                    accountId,
                    null,
                    devRequestDTO.getCharacterId(),
                    devRequestDTO.getNickname()
            );
        }
        User savedUser = userRepository.save(user);

        Dev dev = new Dev(
                devRequestDTO.getDevName(),
                devRequestDTO.getDevEmail(),
                devRequestDTO.getDevPhone(),
                devRequestDTO.getGithubLink(),
                devRequestDTO.getPortfolioList(),
                devRequestDTO.getCareer(),
                devRequestDTO.getTag(),
                devRequestDTO.getHope(),
                savedUser.getUserId()
        );

        List<CategoryInfo> categoryInfoList = devRequestDTO.getCategoryNameList()
                .stream().map(categoryName -> new CategoryInfo(categoryName, new ArrayList<>()))
                .peek(categoryInfo -> categoryInfo.setDev(dev)).toList();

        dev.setCategoryInfoList(categoryInfoList);
        Dev savedDev = devRepository.save(dev);

        user.setDevId(savedDev.getDevId());

        userRepository.save(user);

        // Response 정리되면 Refactoring
        return new UserPartnerSingleResponseDTO(devRepository.save(dev));
    }

    @Transactional
    public UserPartnerSingleResponseDTO updateUserPartner(DevRequestDTO devRequestDTO,
                                                          String accountId) {

        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");
        Dev dev = devViewRepository.findDevByUserId(user.getUserId());
        if(dev == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");

        user.setNickname(devRequestDTO.getNickname());
        dev.setDevName(devRequestDTO.getDevName());
        dev.setDevEmail(devRequestDTO.getDevEmail());
        dev.setDevPhone(devRequestDTO.getDevPhone());
        dev.setGithubLink(devRequestDTO.getGithubLink());
        dev.setPortfolioList(devRequestDTO.getPortfolioList());
        dev.setCareer(devRequestDTO.getCareer());
        dev.setHope(devRequestDTO.getHope());

        userRepository.save(user);

        // Response 정리되면 Refactoring
        return new UserPartnerSingleResponseDTO(devRepository.save(dev));
    }

    public void deleteByAccountId(String accountId) {
        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");
        devRepository.deleteByUserId(user.getUserId());
    }
}
