package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserPartnerConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import com.mtvs.devlinkbackend.oauth2.repository.UserPartnersRepository;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserPartnerService {
    private final UserPartnersRepository userPartnersRepository;
    private final JwtUtil jwtUtil;

    public UserPartnerService(UserPartnersRepository userPartnersRepository, JwtUtil jwtUtil) {
        this.userPartnersRepository = userPartnersRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserPartner registUserPartner(UserPartnerConvertRequestDTO userPartnerConvertRequestDTO,
                                         String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);

        return userPartnersRepository.save(new UserPartner(
                accountId,
                userPartnerConvertRequestDTO.getPurpose(),
                userPartnerConvertRequestDTO.getNickname(),
                userPartnerConvertRequestDTO.getName(),
                userPartnerConvertRequestDTO.getEmail(),
                userPartnerConvertRequestDTO.getPhone(),
                userPartnerConvertRequestDTO.getPortfolioList(),
                userPartnerConvertRequestDTO.getExperience(),
                userPartnerConvertRequestDTO.getSkillSet(),
                userPartnerConvertRequestDTO.getMessage()
        ));
    }

    public UserPartner findUserPartnerByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
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
    public UserPartner updateUserPartner(UserPartnerConvertRequestDTO userPartnerConvertRequestDTO,
                                         String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserPartner userPartner = userPartnersRepository.findUserPartnerByAccountId(accountId);
        if(userPartner == null)
            throw new IllegalArgumentException("잘못된 계정으로 파트너스 정보 수정 시도");

        userPartner.setNickname(userPartnerConvertRequestDTO.getNickname());
        userPartner.setName(userPartnerConvertRequestDTO.getName());
        userPartner.setEmail(userPartnerConvertRequestDTO.getEmail());
        userPartner.setPhone(userPartnerConvertRequestDTO.getPhone());
        userPartner.setPortfolioList(userPartnerConvertRequestDTO.getPortfolioList());
        userPartner.setExperience(userPartnerConvertRequestDTO.getExperience());
        userPartner.setSkillSet(userPartnerConvertRequestDTO.getSkillSet());
        userPartner.setMesssage(userPartnerConvertRequestDTO.getMessage());

        return userPartner;
    }

    public void deleteByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userPartnersRepository.deleteUserByAccountId(accountId);
    }
}
