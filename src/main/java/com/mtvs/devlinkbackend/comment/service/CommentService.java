package com.mtvs.devlinkbackend.comment.service;

import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserViewService userViewService;

    public CommentService(CommentRepository commentRepository, UserViewService userViewService) {
        this.commentRepository = commentRepository;
        this.userViewService = userViewService;
    }

    @Transactional
    public CommentSingleResponseDTO registComment(CommentRegistRequestDTO commentRegistRequestDTO) {
        return new CommentSingleResponseDTO(commentRepository.save(new Comment(
                commentRegistRequestDTO.getContent(),
                commentRegistRequestDTO.getUserId()
        )));
    }

    @Transactional
    public CommentSingleResponseDTO updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, String accountId) {
        Optional<Comment> comment = commentRepository.findById(commentUpdateRequestDTO.getCommentId());
        User user = userViewService.findUserByEpicAccountId(accountId);
        if (comment.isPresent()) {
            Comment foundComment = comment.get();
            if(foundComment.getUserId().equals(user.getUserId())) {
                foundComment.setContent(commentUpdateRequestDTO.getContent());
                return new CommentSingleResponseDTO(foundComment);
            }
            else throw new IllegalArgumentException("다른 사용자가 코멘트 수정 시도 / commentId : "
                    + commentUpdateRequestDTO.getCommentId()
                    + ", accountId : " + accountId);
        }
        else throw new IllegalArgumentException("잘못된 commentId로 코멘트 수정 시도");
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
