package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserPartnerRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerSingleResponseDTO;
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
    public UserPartnerSingleResponseDTO registUserPartner(UserPartnerRequestDTO userPartnerRequestDTO,
                                                          String accountId) {

        return new UserPartnerSingleResponseDTO(userPartnersRepository.save(new UserPartner(
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
        )));
    }

    public UserPartnerSingleResponseDTO findUserPartnerByAccountId(String accountId) {
        return new UserPartnerSingleResponseDTO(userPartnersRepository.findUserPartnerByAccountId(accountId));
    };

    public UserPartnerListResponseDTO findUserPartnersByNameContainingIgnoreCase(String name) {
        return new UserPartnerListResponseDTO(userPartnersRepository.findUserPartnersByNameContainingIgnoreCase(name));
    };

    public UserPartnerListResponseDTO findUserPartnersByNicknameContainingIgnoreCase(String nickname) {
        return new UserPartnerListResponseDTO(
                userPartnersRepository.findUserPartnersByNicknameContainingIgnoreCase(nickname));
    };

    public UserPartnerListResponseDTO findUserPartnersByEmail(String email) {
        return new UserPartnerListResponseDTO(userPartnersRepository.findUserPartnersByEmail(email));
    };

    public UserPartnerSingleResponseDTO findUserPartnerByPhone(String phone) {
        return new UserPartnerSingleResponseDTO(userPartnersRepository.findUserPartnerByPhone(phone));
    };

    @Transactional
    public UserPartnerSingleResponseDTO updateUserPartner(UserPartnerRequestDTO userPartnerRequestDTO,
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

        return new UserPartnerSingleResponseDTO(userPartner);
    }

    public void deleteByAccountId(String accountId) {
        userPartnersRepository.deleteUserByAccountId(accountId);
    }
}
