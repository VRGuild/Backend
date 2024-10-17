package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientIndividualRequestDTO;
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
    public UserClientIndividual registUserClientIndividual(UserClientIndividualRequestDTO userClientIndividualRequestDTO,
                                                           String accountId) {

        return userClientIndividualRepository.save(new UserClientIndividual(
                accountId,
                userClientIndividualRequestDTO.getPurpose(),
                userClientIndividualRequestDTO.getName(),
                userClientIndividualRequestDTO.getPhone()
        ));
    }
    public UserClientIndividual findUserClientIndividualByAccountId(String accountId) {
        return userClientIndividualRepository.findUserClientIndividualByAccountId(accountId);

    };
    public List<UserClientIndividual> findUserClientIndividualsByNameContainingIgnoreCase(String name) {
        return userClientIndividualRepository.findUserClientIndividualsByNameContainingIgnoreCase(name);

    };
    public List<UserClientIndividual> findUserClientIndividualsByPhone(String phone) {
        return userClientIndividualRepository.findUserClientIndividualsByPhone(phone);

    };

    @Transactional
    public UserClientIndividual updateUserClientIndividual(UserClientIndividualRequestDTO userClientIndividualRequestDTO,
                                                           String accountId) {

        UserClientIndividual userClientIndividual = userClientIndividualRepository.findUserClientIndividualByAccountId(accountId);
        if(userClientIndividual == null)
            throw new IllegalArgumentException("잘못된 계정으로 개인 정보 수정 시도");

        userClientIndividual.setName(userClientIndividualRequestDTO.getName());
        userClientIndividual.setPhone(userClientIndividualRequestDTO.getPhone());

        return userClientIndividual;
    }

    public void deleteByAccountId(String accountId) {
        userClientIndividualRepository.deleteByAccountId(accountId);
    };
}
