package com.mtvs.devlinkbackend.reply.controller;

import com.mtvs.devlinkbackend.reply.dto.response.ReplyListResponseDTO;
import com.mtvs.devlinkbackend.reply.dto.response.ReplySingleResponseDTO;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.reply.dto.request.ReplyRegistRequestDTO;
import com.mtvs.devlinkbackend.reply.dto.request.ReplyUpdateRequestDTO;
import com.mtvs.devlinkbackend.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final JwtUtil jwtUtil;

    public ReplyController(ReplyService replyService, JwtUtil jwtUtil) {
        this.replyService = replyService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "새 댓글 등록", description = "질문에 대한 새 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PostMapping
    public ResponseEntity<ReplySingleResponseDTO> registReply(
            @RequestBody ReplyRegistRequestDTO replyRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithAuth(authorizationHeader);
        ReplySingleResponseDTO reply = replyService.registReply(replyRegistRequestDTO, accountId);
        return ResponseEntity.ok(reply);
    }

    @Operation(summary = "PK로 댓글 조회", description = "댓글 ID, 즉 PK를 통해 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplySingleResponseDTO> findReplyByReplyId(@PathVariable Long replyId) {
        ReplySingleResponseDTO reply = replyService.findReplyByReplyId(replyId);
        return reply != null ? ResponseEntity.ok(reply) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "질문 ID로 댓글 목록 조회", description = "질문 ID로 연결된 모든 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @GetMapping("/question/{questionId}")
    public ResponseEntity<ReplyListResponseDTO> findRepliesByQuestionId(@PathVariable Long questionId) {
        ReplyListResponseDTO replies = replyService.findRepliesByQuestionId(questionId);
        return ResponseEntity.ok(replies);
    }

    @Operation(summary = "계정 ID로 댓글 목록 조회", description = "인증된 계정과 연결된 모든 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @GetMapping("/account")
    public ResponseEntity<ReplyListResponseDTO> findRepliesByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        ReplyListResponseDTO replies = replyService.findRepliesByAccountId(accountId);
        return ResponseEntity.ok(replies);
    }

    @Operation(summary = "댓글 수정", description = "제공된 데이터를 기반으로 특정 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 수정 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PatchMapping
    public ResponseEntity<ReplySingleResponseDTO> updateReply(
            @RequestBody ReplyUpdateRequestDTO replyUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        try {
            ReplySingleResponseDTO updatedReply = replyService.updateReply(replyUpdateRequestDTO, accountId);
            return ResponseEntity.ok(updatedReply);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글이 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long replyId) {

        replyService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }
}