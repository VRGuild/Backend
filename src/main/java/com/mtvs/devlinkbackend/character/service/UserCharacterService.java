package com.mtvs.devlinkbackend.character.service;

import com.mtvs.devlinkbackend.character.dto.UserCharacterRegistRequestDTO;
import com.mtvs.devlinkbackend.character.dto.UserCharacterUpdateRequestDTO;
import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.repository.UserCharacterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserCharacterService {
    private final UserCharacterRepository userCharacterRepository;

    public UserCharacterService(UserCharacterRepository userCharacterRepository) {
        this.userCharacterRepository = userCharacterRepository;
    }

    @Transactional
    public UserCharacter registCharacter(UserCharacterRegistRequestDTO userCharacterRegistRequestDTO, String accountId) {
        return userCharacterRepository.save(new UserCharacter(
                accountId,
                userCharacterRegistRequestDTO.getStatus()
        ));
    }

    public UserCharacter findCharacterByAccountId(String accountId) {
        return userCharacterRepository.findByAccountId(accountId);
    }

    @Transactional
    public UserCharacter updateCharacter(UserCharacterUpdateRequestDTO userCharacterUpdateRequestDTO, String accountId) {
        UserCharacter userCharacter = userCharacterRepository.findByAccountId(accountId);
        if(userCharacter == null)
            throw new IllegalArgumentException("잘못된 계정으로 캐릭터 수정 시도");

        userCharacter.setStatus(userCharacterUpdateRequestDTO.getStatus());
        return userCharacter;
    }

    @Transactional
    public void deleteCharacterByAccountId(String accountId) {
        userCharacterRepository.deleteByAccountId(accountId);
    }
}
