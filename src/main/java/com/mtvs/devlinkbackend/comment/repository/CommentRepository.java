package com.mtvs.devlinkbackend.comment.repository;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAccountId(String accountId);
    List<Comment> findCommentsByProject_ProjectId(Long requestId);

    @Query("SELECT c FROM Comment c WHERE c.project.projectId = :projectId")
    List<Comment> findCommentIdsByProjectId(@Param("projectId") Long projectId);
}
