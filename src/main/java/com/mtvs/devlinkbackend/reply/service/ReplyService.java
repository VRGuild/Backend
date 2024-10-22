package com.mtvs.devlinkbackend.reply.service;

import com.mtvs.devlinkbackend.question.repository.QuestionRepository;
import com.mtvs.devlinkbackend.reply.dto.response.ReplyListResponseDTO;
import com.mtvs.devlinkbackend.reply.dto.request.ReplyRegistRequestDTO;
import com.mtvs.devlinkbackend.reply.dto.response.ReplySingleResponseDTO;
import com.mtvs.devlinkbackend.reply.dto.request.ReplyUpdateRequestDTO;
import com.mtvs.devlinkbackend.reply.entity.Reply;
import com.mtvs.devlinkbackend.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final QuestionRepository questionRepository;

    public ReplyService(ReplyRepository replyRepository, QuestionRepository questionRepository) {
        this.replyRepository = replyRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public ReplySingleResponseDTO registReply(ReplyRegistRequestDTO replyRegistRequestDTO, String accountId) {
        return new ReplySingleResponseDTO(replyRepository.save(
                new Reply(
                        replyRegistRequestDTO.getContent(),
                        accountId,
                        questionRepository.getReferenceById(replyRegistRequestDTO.getQuestionId())
                )));
    }

    public ReplySingleResponseDTO findReplyByReplyId(Long replyId) {
        return new ReplySingleResponseDTO(replyRepository.findById(replyId).orElse(null));
    }

    public ReplyListResponseDTO findRepliesByQuestionId(Long questionId) {
        return new ReplyListResponseDTO(replyRepository.findRepliesByQuestion_QuestionId(questionId));
    }

    public ReplyListResponseDTO findRepliesByAccountId(String accountId) {
        return new ReplyListResponseDTO(replyRepository.findRepliesByAccountId(accountId));
    }

    @Transactional
    public ReplySingleResponseDTO updateReply(ReplyUpdateRequestDTO replyUpdateRequestDTO, String accountId) {
        Optional<Reply> reply = replyRepository.findById(replyUpdateRequestDTO.getReplyId());
        if (reply.isPresent()) {
            Reply foundReply = reply.get();
            if (foundReply.getAccountId().equals(accountId)) {
                foundReply.setContent(replyUpdateRequestDTO.getContent());
                return new ReplySingleResponseDTO(foundReply);
            } else throw new IllegalArgumentException("Reply Update Error : 다른 사용자가 남의 질문 내용 변경 시도");
        } else throw new IllegalArgumentException(
                "Reply not found while updating reply id : " + replyUpdateRequestDTO.getReplyId());
    }

    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
        System.out.println("답변ID = " + replyId + " ,삭제 시간 : " + LocalDateTime.now());
    }
}
