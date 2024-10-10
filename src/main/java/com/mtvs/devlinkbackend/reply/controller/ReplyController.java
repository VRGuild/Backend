package com.mtvs.devlinkbackend.reply.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.reply.dto.ReplyRegistRequestDTO;
import com.mtvs.devlinkbackend.reply.dto.ReplyUpdateRequestDTO;
import com.mtvs.devlinkbackend.reply.entity.Reply;
import com.mtvs.devlinkbackend.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final JwtUtil jwtUtil;

    public ReplyController(ReplyService replyService, JwtUtil jwtUtil) {
        this.replyService = replyService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Reply> registReply(
            @RequestBody ReplyRegistRequestDTO replyRegistRequestDTO,
            @RequestHeader(name = "Authorization") String token) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithAuth(token);
        Reply reply = replyService.registReply(replyRegistRequestDTO, accountId);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<Reply> findReplyByReplyId(@PathVariable Long replyId) {
        Reply reply = replyService.findReplyByReplyId(replyId);
        return reply != null ? ResponseEntity.ok(reply) : ResponseEntity.notFound().build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Reply>> findRepliesByQuestionId(@PathVariable Long questionId) {
        List<Reply> replies = replyService.findRepliesByQuestionId(questionId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Reply>> findRepliesByAccountId(
            @RequestHeader(name = "Authorization") String token) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(token);
        List<Reply> replies = replyService.findRepliesByAccountId(accountId);
        return ResponseEntity.ok(replies);
    }

    @PatchMapping
    public ResponseEntity<Reply> updateReply(
            @RequestBody ReplyUpdateRequestDTO replyUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String token) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(token);
        try {
            Reply updatedReply = replyService.updateReply(replyUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedReply);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long replyId) {

        replyService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }
}