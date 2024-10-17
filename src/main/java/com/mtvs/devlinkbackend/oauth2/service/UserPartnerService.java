package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserPartnerRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import com.mtvs.devlinkbackend.oauth2.repository.UserPartnersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserPartnerService {
    private final UserPartnersRepository userPartnersRepository;

    public UserPartnerService(UserPartnersRepository userPartnersRepository) {
        this.userPartnersRepository = userPartnersRepository;
    }

    @Transactional
    public UserPartner registUserPartner(UserPartnerRequestDTO userPartnerRequestDTO,
                                         String accountId) {

        return userPartnersRepository.save(new UserPartner(
                accountId,
                userPartnerRequestDTO.getPurpose(),
                userPartnerRequestDTO.getNickname(),
                userPartnerRequestDTO.getName(),
                userPartnerRequestDTO.getEmail(),
                userPartnerRequestDTO.getPhone(),
                userPartnerRequestDTO.getPortfolioList(),
                userPartnerRequestDTO.getExperience(),
                userPartnerRequestDTO.getSkillSet(),
                userPartnerRequestDTO.getMessage()
        ));
    }

    public UserPartner findUserPartnerByAccountId(String accountId) {
        return userPartnersRepository.findUserPartnerByAccountId(accountId);
    };

    public List<UserPartner> findUserPartnersByNameContainingIgnoreCase(String name) {
        return userPartnersRepository.findUserPartnersByNameContainingIgnoreCase(name);
    };

    public List<UserPartner> findUserPartnersByNicknameContainingIgnoreCase(String nickname) {
        return userPartnersRepository.findUserPartnersByNicknameContainingIgnoreCase(nickname);
    };

    public List<UserPartner> findUserPartnersByEmail(String email) {
        return userPartnersRepository.findUserPartnersByEmail(email);
    };

    public UserPartner findUserPartnerByPhone(String phone) {
        return userPartnersRepository.findUserPartnerByPhone(phone);
    };

    @Transactional
    public UserPartner updateUserPartner(UserPartnerRequestDTO userPartnerRequestDTO,
                                         String accountId) {

        UserPartner userPartner = userPartnersRepository.findUserPartnerByAccountId(accountId);
        if(userPartner == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");

        userPartner.setNickname(userPartnerRequestDTO.getNickname());
        userPartner.setName(userPartnerRequestDTO.getName());
        userPartner.setEmail(userPartnerRequestDTO.getEmail());
        userPartner.setPhone(userPartnerRequestDTO.getPhone());
        userPartner.setPortfolioList(userPartnerRequestDTO.getPortfolioList());
        userPartner.setExperience(userPartnerRequestDTO.getExperience());
        userPartner.setSkillSet(userPartnerRequestDTO.getSkillSet());
        userPartner.setMesssage(userPartnerRequestDTO.getMessage());

        return userPartner;
    }

    public void deleteByAccountId(String accountId) {
        userPartnersRepository.deleteUserByAccountId(accountId);
    }
}
