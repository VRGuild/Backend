package com.mtvs.devlinkbackend.comment.service;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.service.UserCharacterViewService;
import com.mtvs.devlinkbackend.comment.dto.response.CommentDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.sub.CommentDetailDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentViewRepository;
import com.mtvs.devlinkbackend.guild.repository.GuildRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.CharacterInfoDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.DevInfoDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserDetailResponseDTO;
import com.mtvs.devlinkbackend.user.query.repository.SkillCategoryInfoViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentViewService {

    private final CommentViewRepository commentViewRepository;
    private final UserViewService userViewService;
    private final UserCharacterViewService userCharacterViewService;
    private final UserViewRepository userViewRepository;
    private final SkillCategoryInfoViewRepository skillCategoryInfoViewRepository;
    private final GuildRepository guildRepository;

    public CommentViewService(CommentViewRepository commentViewRepository, UserViewService userViewService, UserCharacterViewService userCharacterViewService, UserViewRepository userViewRepository, SkillCategoryInfoViewRepository skillCategoryInfoViewRepository, GuildRepository guildRepository) {
        this.commentViewRepository = commentViewRepository;
        this.userViewService = userViewService;
        this.userCharacterViewService = userCharacterViewService;
        this.userViewRepository = userViewRepository;
        this.skillCategoryInfoViewRepository = skillCategoryInfoViewRepository;
        this.guildRepository = guildRepository;
    }

    public CommentSingleResponseDTO findCommentByCommentId(Long commentId) {
        return new CommentSingleResponseDTO(commentViewRepository.findById(commentId).orElse(null));
    }

    public CommentListResponseDTO findCommentsByAccountId(String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        return new CommentListResponseDTO(commentViewRepository.findAllByUserId(user.getUserId()));
    }

    public CommentListResponseDTO findCommentsByCommentIdList(List<Long> commentIdList) {
        return new CommentListResponseDTO(commentViewRepository.findByCommentIdIn(commentIdList));
    }

    public CommentDetailSingleResponseDTO findCommentDetailByCommentId(Long commentId) {
        Comment comment = commentViewRepository.findById(commentId).orElse(null);
        if (comment == null)
            throw new IllegalArgumentException("잘못된 댓글 ID로 접근중");
        User foundUser = userViewRepository.findById(comment.getUserId()).orElse(null);

        UserCharacter foundUserCharacter =
                foundUser != null ?
                        userCharacterViewService.findCharacterByCharacterId(foundUser.getCharacterId()).getData() : null;

        CharacterInfoDTO characterInfoDTO =
                foundUserCharacter != null ?
                        new CharacterInfoDTO(
                                foundUserCharacter.getGuildId() != null ?
                                        guildRepository.findByGuildId(foundUserCharacter.getGuildId()) : null,
                                foundUserCharacter.getCharacterPicture())
                        : null;

        DevInfoDTO devInfoDTO =
                new DevInfoDTO(
                        foundUser != null ?
                                skillCategoryInfoViewRepository.findByDev_DevId(foundUser.getDevId()) : null
                );

        return new CommentDetailSingleResponseDTO(
                new CommentDetailDTO(
                        commentId,
                        new UserDetailResponseDTO(
                                comment.getUserId(),
                                characterInfoDTO,
                                devInfoDTO,
                                foundUser.getExperienceValue(),
                                foundUser.getBusinessId(),
                                foundUser.getNickname()),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )
        );
    }
}
