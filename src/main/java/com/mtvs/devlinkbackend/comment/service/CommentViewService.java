package com.mtvs.devlinkbackend.comment.service;

import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.repository.CommentViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentViewService {

    private final CommentViewRepository commentViewRepository;
    private final UserViewService userViewService;

    public CommentViewService(CommentViewRepository commentViewRepository, UserViewService userViewService) {
        this.commentViewRepository = commentViewRepository;
        this.userViewService = userViewService;
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
}
