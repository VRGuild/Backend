package com.mtvs.devlinkbackend.comment.repository;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAccountId(String accountId);
    List<Comment> findCommentsByRequest_RequestId(Long requestId);
}
