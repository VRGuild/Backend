package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserPartnerRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerSingleResponseDTO;
import com.mtvs.devlinkbackend.oauth2.entity.Skill;
import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import com.mtvs.devlinkbackend.oauth2.repository.UserPartnersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPartnerService {
    private final UserPartnersRepository userPartnersRepository;

    public UserPartnerService(UserPartnersRepository userPartnersRepository) {
        this.userPartnersRepository = userPartnersRepository;
    }

    @Transactional
    public UserPartnerSingleResponseDTO registUserPartner(UserPartnerRequestDTO userPartnerRequestDTO,
                                                          String accountId) {

        UserPartner userPartner = new UserPartner(
                accountId,
                userPartnerRequestDTO.getPurpose(),
                userPartnerRequestDTO.getNickname(),
                userPartnerRequestDTO.getName(),
                userPartnerRequestDTO.getEmail(),
                userPartnerRequestDTO.getPhone(),
                userPartnerRequestDTO.getGithubLink(),
                userPartnerRequestDTO.getPortfolioList(),
                userPartnerRequestDTO.getExperience(),
                userPartnerRequestDTO.getMessage()
        );

        List<Skill> skills = userPartnerRequestDTO.getSkill().stream()
                .map(skillDTO -> new Skill(skillDTO.getSkillCategory(), skillDTO.getSkillName(), Integer.parseInt(skillDTO.getSkillLevel())))
                .peek(skill -> skill.setUserPartner(userPartner)) // 각 Skill 엔티티에 UserPartner 설정
                .toList();

        userPartner.setSkillSet(skills);


        return new UserPartnerSingleResponseDTO(userPartnersRepository.save(userPartner));
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
        userPartner.setGithubLink(userPartnerRequestDTO.getGithubLink());
        userPartner.setPortfolioList(userPartnerRequestDTO.getPortfolioList());
        userPartner.setExperience(userPartnerRequestDTO.getExperience());
        userPartner.setMessage(userPartnerRequestDTO.getMessage());

        // 기존 Skill 삭제 (orphanRemoval=true 옵션이 설정되어 있다면 자동 삭제됨)
        userPartner.getSkillSet().clear();

        // 새로운 Skill 리스트 설정
        List<Skill> newSkills = userPartnerRequestDTO.getSkill().stream()
                .map(skillDTO -> new Skill(skillDTO.getSkillCategory(), skillDTO.getSkillName(), Integer.parseInt(skillDTO.getSkillLevel())))
                .peek(skill -> skill.setUserPartner(userPartner)) // 각 Skill 엔티티에 UserPartner 설정
                .toList();

        userPartner.setSkillSet(newSkills);


        return new UserPartnerSingleResponseDTO(userPartnersRepository.save(userPartner));
    }

    public void deleteByAccountId(String accountId) {
        userPartnersRepository.deleteUserByAccountId(accountId);
    }
}
