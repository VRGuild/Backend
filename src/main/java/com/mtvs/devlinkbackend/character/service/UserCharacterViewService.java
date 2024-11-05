package com.mtvs.devlinkbackend.character.service;

import com.mtvs.devlinkbackend.character.dto.response.UserCharacterSingleResponseDTO;
import com.mtvs.devlinkbackend.character.repository.UserCharacterRepository;
import com.mtvs.devlinkbackend.character.repository.UserCharacterViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCharacterViewService {
    private final UserViewRepository userViewRepository;
    private final UserCharacterViewRepository userCharacterViewRepository;

    public UserCharacterViewService(UserViewRepository userViewRepository, UserCharacterViewRepository userCharacterViewRepository, UserCharacterRepository userCharacterRepository) {
        this.userViewRepository = userViewRepository;
        this.userCharacterViewRepository = userCharacterViewRepository;
    }

    public UserCharacterSingleResponseDTO findCharacterByAccountId(String accountId) {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        return new UserCharacterSingleResponseDTO(userCharacterViewRepository.findByUserId(foundUser.getUserId()));
    }

    public UserCharacterSingleResponseDTO findCharacterByUserId(Long userId) {
        return new UserCharacterSingleResponseDTO(userCharacterViewRepository.findByUserId(userId));
    }

    public UserCharacterSingleResponseDTO findCharacterByCharacterId(Long characterId) {
        return new UserCharacterSingleResponseDTO(userCharacterViewRepository.findById(characterId).orElse(null));
    }
}
