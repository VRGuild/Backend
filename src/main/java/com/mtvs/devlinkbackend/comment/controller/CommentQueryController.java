package com.mtvs.devlinkbackend.comment.controller;

import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.service.CommentViewService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentQueryController {

    private final JwtUtil jwtUtil;
    private final CommentViewService commentViewService;

    public CommentQueryController(JwtUtil jwtUtil, CommentViewService commentViewService) {
        this.jwtUtil = jwtUtil;
        this.commentViewService = commentViewService;
    }

    @Operation(summary = "댓글 조회", description = "ID를 사용하여 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없습니다.")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentSingleResponseDTO> findCommentByCommentId(@PathVariable Long commentId) {
        CommentSingleResponseDTO comment = commentViewService.findCommentByCommentId(commentId);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "CommentIdList로 댓글 조회", description = "CommentIdList에 포함된 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/list")
    public ResponseEntity<CommentListResponseDTO> findCommentsByCommentIdList(
            @RequestParam("ids") Long[] idList) {

        List<Long> commentIdList = Arrays.stream(idList).toList();
        CommentListResponseDTO comments = commentViewService.findCommentsByCommentIdList(commentIdList);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "사용자 ID로 댓글 조회", description = "특정 사용자가 작성한 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자의 댓글 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/account")
    public ResponseEntity<CommentListResponseDTO> findCommentsByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        CommentListResponseDTO comments = commentViewService.findCommentsByAccountId(accountId);
        return ResponseEntity.ok(comments);
    }
}
