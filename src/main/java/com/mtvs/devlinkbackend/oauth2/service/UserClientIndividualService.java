package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserClientIndividualRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientIndividualListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientIndividualSingleResponseDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientIndividual;
import com.mtvs.devlinkbackend.oauth2.repository.UserClientIndividualRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserClientIndividualService {

    private final UserClientIndividualRepository userClientIndividualRepository;

    public UserClientIndividualService(UserClientIndividualRepository userClientIndividualRepository) {
        this.userClientIndividualRepository = userClientIndividualRepository;
    }

    @Transactional
    public UserClientIndividualSingleResponseDTO registUserClientIndividual(UserClientIndividualRequestDTO userClientIndividualRequestDTO,
                                                                            String accountId) {

        return new UserClientIndividualSingleResponseDTO(userClientIndividualRepository.save(new UserClientIndividual(
                accountId,
                userClientIndividualRequestDTO.getPurpose(),
                userClientIndividualRequestDTO.getName(),
                userClientIndividualRequestDTO.getPhone()
        )));
    }
    public UserClientIndividualSingleResponseDTO findUserClientIndividualByAccountId(String accountId) {
        return new UserClientIndividualSingleResponseDTO(
                userClientIndividualRepository.findUserClientIndividualByAccountId(accountId));

    };
    public UserClientIndividualListResponseDTO findUserClientIndividualsByNameContainingIgnoreCase(String name) {
        return new UserClientIndividualListResponseDTO(
                userClientIndividualRepository.findUserClientIndividualsByNameContainingIgnoreCase(name));

    };
    public UserClientIndividualListResponseDTO findUserClientIndividualsByPhone(String phone) {
        return new UserClientIndividualListResponseDTO(userClientIndividualRepository.findUserClientIndividualsByPhone(phone));

    };

    @Transactional
    public UserClientIndividualSingleResponseDTO updateUserClientIndividual(UserClientIndividualRequestDTO userClientIndividualRequestDTO,
                                                           String accountId) {

        UserClientIndividual userClientIndividual = userClientIndividualRepository.findUserClientIndividualByAccountId(accountId);
        if(userClientIndividual == null)
            throw new IllegalArgumentException("잘못된 계정으로 개인 정보 수정 시도");

        userClientIndividual.setName(userClientIndividualRequestDTO.getName());
        userClientIndividual.setPhone(userClientIndividualRequestDTO.getPhone());

        return new UserClientIndividualSingleResponseDTO(userClientIndividual);
    }

    public void deleteByAccountId(String accountId) {
        userClientIndividualRepository.deleteByAccountId(accountId);
    };
}
