package com.mtvs.devlinkbackend.character.service;

import com.mtvs.devlinkbackend.character.dto.request.UserCharacterRegistRequestDTO;
import com.mtvs.devlinkbackend.character.dto.response.UserCharacterSingleResponseDTO;
import com.mtvs.devlinkbackend.character.dto.request.UserCharacterUpdateRequestDTO;
import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.repository.UserCharacterRepository;
import com.mtvs.devlinkbackend.character.repository.UserCharacterViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserCharacterService {
    private final UserCharacterRepository userCharacterRepository;
    private final UserViewRepository userViewRepository;
    private final UserCharacterViewRepository userCharacterViewRepository;
    private final UserRepository userRepository;
    private final UserViewService userViewService;

    public UserCharacterService(UserCharacterRepository userCharacterRepository, UserViewRepository userViewRepository, UserCharacterViewRepository userCharacterViewRepository, UserRepository userRepository, UserViewService userViewService) {
        this.userCharacterRepository = userCharacterRepository;
        this.userViewRepository = userViewRepository;
        this.userCharacterViewRepository = userCharacterViewRepository;
        this.userRepository = userRepository;
        this.userViewService = userViewService;
    }

    @Transactional
    public UserCharacterSingleResponseDTO registCharacter(UserCharacterRegistRequestDTO userCharacterRegistRequestDTO, String accountId) {
        UserCharacter userCharacter = userCharacterRepository.save(new UserCharacter(
                userCharacterRegistRequestDTO.getGuildId(),
                userCharacterRegistRequestDTO.getTeamIdList(),
                userCharacterRegistRequestDTO.getCharacterPicture(),
                userCharacterRegistRequestDTO.getCustomList(),
                userCharacterRegistRequestDTO.getUserId()
        ));

        User user = userViewService.findUserByEpicAccountId(accountId);
        user.setCharacterId(userCharacter.getCharacterId());

        userRepository.save(user);

        return new UserCharacterSingleResponseDTO(userCharacter);
    }

    @Transactional
    public UserCharacterSingleResponseDTO updateCharacter(UserCharacterUpdateRequestDTO userCharacterUpdateRequestDTO) {
        UserCharacter userCharacter = userCharacterViewRepository.findByUserId(userCharacterUpdateRequestDTO.getUserId());
        if(userCharacter == null)
            throw new IllegalArgumentException("잘못된 계정으로 캐릭터 수정 시도");

        userCharacter.setGuildId(userCharacterUpdateRequestDTO.getGuildId());
        userCharacter.setCharacterPicture(userCharacter.getCharacterPicture());
        userCharacter.setCustomList(userCharacterUpdateRequestDTO.getCustomList());
        userCharacter.setTeamIdList(userCharacterUpdateRequestDTO.getTeamIdList());

        return new UserCharacterSingleResponseDTO(userCharacter);
    }

    @Transactional
    public void deleteCharacterByAccountId(String accountId) {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        userCharacterRepository.deleteByUserId(foundUser.getUserId());
    }
}
