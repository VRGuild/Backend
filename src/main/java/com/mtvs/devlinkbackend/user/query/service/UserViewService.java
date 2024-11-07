package com.mtvs.devlinkbackend.user.query.service;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.repository.UserCharacterRepository;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.guild.repository.GuildViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.CharacterInfoDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.DevInfoDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserDetailResponseDTO;
import com.mtvs.devlinkbackend.user.query.repository.SkillCategoryInfoViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserViewService {
    private final JwtUtil jwtUtil;
    private final UserViewRepository userViewRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final SkillCategoryInfoViewRepository skillCategoryInfoViewRepository;
    private final GuildViewRepository guildViewRepository;

    public UserViewService(JwtUtil jwtUtil, UserViewRepository userViewRepository, UserCharacterRepository userCharacterRepository, SkillCategoryInfoViewRepository skillCategoryInfoViewRepository, GuildViewRepository guildViewRepository) {
        this.jwtUtil = jwtUtil;
        this.userViewRepository = userViewRepository;
        this.userCharacterRepository = userCharacterRepository;
        this.skillCategoryInfoViewRepository = skillCategoryInfoViewRepository;
        this.guildViewRepository = guildViewRepository;
    }

    public Boolean isExistedUserByEpicAccountId(String accountId) throws Exception {
        return userViewRepository.findUserByEpicAccountId(accountId) != null;
    }

    public User findUserByEpicAccountId(String accountId) {
        return userViewRepository.findUserByEpicAccountId(accountId);
    }

    public UserSingleResponseDTO findUserByUserId(Long userId) {
        return new UserSingleResponseDTO(userViewRepository.findById(userId).orElse(null));
    }

    @Transactional
    public UserDetailSingleResponseDTO findUserDetailByUserId(Long userId) {
        User foundUser = userViewRepository.findById(userId).orElse(null);
        UserCharacter foundUserCharacter =
                foundUser != null ?
                        userCharacterRepository.findById(foundUser.getCharacterId()).orElse(null) : null;

        CharacterInfoDTO characterInfoDTO =
                foundUserCharacter != null ?
                        new CharacterInfoDTO(
                                foundUserCharacter.getGuildId() != null ?
                                        guildViewRepository.findByGuildId(foundUserCharacter.getGuildId()) : null,
                                foundUserCharacter.getCharacterPicture()) :
                        null;
        DevInfoDTO devInfoDTO =
                new DevInfoDTO(
                        foundUser != null ?
                                skillCategoryInfoViewRepository.findByDev_DevId(foundUser.getDevId()) : null
                );
        return new UserDetailSingleResponseDTO(
                new UserDetailResponseDTO(
                        userId,
                        characterInfoDTO,
                        devInfoDTO,
                        foundUser.getExperienceValue(),
                        foundUser.getBusinessId(),
                        foundUser.getNickname()));
    }
}
