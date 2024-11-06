package com.mtvs.devlinkbackend.comment.repository;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentViewRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long userId);

    List<Comment> findByCommentIdIn(List<Long> commentIdList);
}
