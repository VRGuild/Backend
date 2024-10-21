package com.mtvs.devlinkbackend.comment.service;

import com.mtvs.devlinkbackend.comment.dto.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Comment registComment(CommentRegistRequestDTO commentRegistRequestDTO, String accountId) {
        Project project = projectRepository.findById(commentRegistRequestDTO.getRequestId()).orElse(null);
        return commentRepository.save(new Comment(
                commentRegistRequestDTO.getContent(),
                accountId,
                project
        ));
    }

    public Comment findCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public List<Comment> findCommentsByProjectId(Long requestId) {
        return commentRepository.findCommentsByProject_ProjectId(requestId);
    }

    public List<Comment> findCommentsByAccountId(String accountId) {
        return commentRepository.findCommentsByAccountId(accountId);
    }

    @Transactional
    public Comment updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, String accountId) {
        Optional<Comment> comment = commentRepository.findById(commentUpdateRequestDTO.getCommentId());
        if (comment.isPresent()) {
            Comment foundComment = comment.get();
            if(foundComment.getAccountId().equals(accountId)) {
                foundComment.setContent(commentUpdateRequestDTO.getContent());
                return foundComment;
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
