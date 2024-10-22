package com.mtvs.devlinkbackend.comment.service;

import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;

    public CommentService(CommentRepository commentRepository, ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public CommentSingleResponseDTO registComment(CommentRegistRequestDTO commentRegistRequestDTO, String accountId) {
        Project project = projectRepository.findById(commentRegistRequestDTO.getRequestId()).orElse(null);
        return new CommentSingleResponseDTO(commentRepository.save(new Comment(
                commentRegistRequestDTO.getContent(),
                accountId,
                project
        )));
    }

    public CommentSingleResponseDTO findCommentByCommentId(Long commentId) {
        return new CommentSingleResponseDTO(commentRepository.findById(commentId).orElse(null));
    }

    public CommentListResponseDTO findCommentsByProjectId(Long requestId) {
        return new CommentListResponseDTO(commentRepository.findCommentsByProject_ProjectId(requestId));
    }

    public CommentListResponseDTO findCommentsByAccountId(String accountId) {
        return new CommentListResponseDTO(commentRepository.findCommentsByAccountId(accountId));
    }

    @Transactional
    public CommentSingleResponseDTO updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, String accountId) {
        Optional<Comment> comment = commentRepository.findById(commentUpdateRequestDTO.getCommentId());
        if (comment.isPresent()) {
            Comment foundComment = comment.get();
            if(foundComment.getAccountId().equals(accountId)) {
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
