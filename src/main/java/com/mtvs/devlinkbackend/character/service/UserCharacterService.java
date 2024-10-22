package com.mtvs.devlinkbackend.character.service;

import com.mtvs.devlinkbackend.character.dto.request.UserCharacterRegistRequestDTO;
import com.mtvs.devlinkbackend.character.dto.response.UserCharacterSingleResponseDTO;
import com.mtvs.devlinkbackend.character.dto.request.UserCharacterUpdateRequestDTO;
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
    public UserCharacterSingleResponseDTO registCharacter(UserCharacterRegistRequestDTO userCharacterRegistRequestDTO, String accountId) {
        return new UserCharacterSingleResponseDTO(userCharacterRepository.save(new UserCharacter(
                accountId,
                userCharacterRegistRequestDTO.getStatus()
        )));
    }

    public UserCharacterSingleResponseDTO findCharacterByAccountId(String accountId) {
        return new UserCharacterSingleResponseDTO(userCharacterRepository.findByAccountId(accountId));
    }

    @Transactional
    public UserCharacterSingleResponseDTO updateCharacter(UserCharacterUpdateRequestDTO userCharacterUpdateRequestDTO, String accountId) {
        UserCharacter userCharacter = userCharacterRepository.findByAccountId(accountId);
        if(userCharacter == null)
            throw new IllegalArgumentException("잘못된 계정으로 캐릭터 수정 시도");

        userCharacter.setStatus(userCharacterUpdateRequestDTO.getStatus());
        return new UserCharacterSingleResponseDTO(userCharacter);
    }

    @Transactional
    public void deleteCharacterByAccountId(String accountId) {
        userCharacterRepository.deleteByAccountId(accountId);
    }
}
