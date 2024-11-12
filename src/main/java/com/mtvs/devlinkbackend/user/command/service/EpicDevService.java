package com.mtvs.devlinkbackend.user.command.service;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.service.UserCharacterViewService;
import com.mtvs.devlinkbackend.file.service.FileUploadService;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevRegistRequestDTO;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevInfoRequestDTO;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevUpdateRequestDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.DevSingleResponseDTO;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.DevRepository;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.repository.DevViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EpicDevService {
    private final DevRepository devRepository;
    private final UserRepository userRepository;
    private final UserViewRepository userViewRepository;
    private final DevViewRepository devViewRepository;
    private final FileUploadService fileUploadService;
    private final UserCharacterViewService userCharacterViewService;

    public EpicDevService(DevRepository devRepository, UserRepository userRepository, UserViewRepository userViewRepository, DevViewRepository devViewRepository, FileUploadService fileUploadService, UserCharacterViewService userCharacterViewService) {
        this.devRepository = devRepository;
        this.userRepository = userRepository;
        this.userViewRepository = userViewRepository;
        this.devViewRepository = devViewRepository;
        this.fileUploadService = fileUploadService;
        this.userCharacterViewService = userCharacterViewService;
    }

    @Transactional
    public DevSingleResponseDTO registDev(DevRegistRequestDTO devRegistRequestDTO,
                                          String accountId) throws IOException {
        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("등록되지 않은 user로 dev 정보 입력 시도");

        UserCharacter userCharacter = userCharacterViewService.findCharacterByUserId(user.getUserId()).getData();
        if (userCharacter != null) {
            user.setCharacterId(userCharacter.getCharacterId());
            user.setNickname(devRegistRequestDTO.getNickname());
        }


        User savedUser = userRepository.save(user);

        DevInfoRequestDTO devInfoRequestDTO = devRegistRequestDTO.getDevInfo();
        Dev dev = new Dev(
                devInfoRequestDTO.getDevName(),
                devInfoRequestDTO.getDevEmail(),
                devInfoRequestDTO.getDevPhone(),
                devInfoRequestDTO.getGithubLink(),
                devInfoRequestDTO.getPortfolioList(),
                devInfoRequestDTO.getCareer(),
                devInfoRequestDTO.getTag(),
                devInfoRequestDTO.getHope(),
                savedUser.getUserId()
        );


        List<SkillCategoryInfo> skillCategoryInfoList = devInfoRequestDTO.getCategoryNameList()
                .stream().map(categoryName -> new SkillCategoryInfo(categoryName, new ArrayList<>()))
                .peek(skillCategoryInfo -> skillCategoryInfo.setDev(dev)).toList();

        dev.setSkillCategoryList(skillCategoryInfoList);
        Dev savedDev = devRepository.save(dev);

        user.setDevId(savedDev.getDevId());

        userRepository.save(user);

        // Response 정리되면 Refactoring
        return new DevSingleResponseDTO(savedDev);
    }

    @Transactional
    public DevSingleResponseDTO updateUserPartner(DevUpdateRequestDTO devUpdateRequestDTO,
                                                  String accountId) {

        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");
        Dev dev = devViewRepository.findDevByUserId(user.getUserId());
        if(dev == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");

        DevInfoRequestDTO devInfoRequestDTO = devUpdateRequestDTO.getDevInfo();
        user.setNickname(devUpdateRequestDTO.getNickname());
        dev.setDevName(devInfoRequestDTO.getDevName());
        dev.setDevEmail(devInfoRequestDTO.getDevEmail());
        dev.setDevPhone(devInfoRequestDTO.getDevPhone());
        dev.setGithubLink(devInfoRequestDTO.getGithubLink());
        //TODO:: 해당 부분 또한 이전 portfolioFile 삭제 이후 새로 업로드한 URL로 업데이트 예정
        dev.setPortfolioUrlList(devInfoRequestDTO.getPortfolioList());
        dev.setCareer(devInfoRequestDTO.getCareer());
        dev.setHope(devInfoRequestDTO.getHope());

        userRepository.save(user);

        // Response 정리되면 Refactoring
        return new DevSingleResponseDTO(devRepository.save(dev));
    }

    public void deleteByAccountId(String accountId) {
        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");
        devRepository.deleteByUserId(user.getUserId());
    }
}
