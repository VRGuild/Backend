package com.mtvs.devlinkbackend.reply.repository;

import com.mtvs.devlinkbackend.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByAccountId(String accountId);
    List<Reply> findRepliesByQuestion_QuestionId(Long questionId);
}
