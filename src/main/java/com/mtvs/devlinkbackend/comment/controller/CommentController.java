package com.mtvs.devlinkbackend.comment.controller;

import com.mtvs.devlinkbackend.comment.dto.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.service.CommentService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "댓글 등록", description = "특정 요청에 대한 새로운 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 요청을 찾을 수 없습니다.")
    })
    @PostMapping
    public ResponseEntity<Comment> registComment(
            @RequestBody CommentRegistRequestDTO commentRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        Comment comment = commentService.registComment(commentRegistRequestDTO, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Operation(summary = "댓글 조회", description = "ID를 사용하여 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없습니다.")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> findCommentByCommentId(@PathVariable Long commentId) {
        Comment comment = commentService.findCommentByCommentId(commentId);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "요청 ID로 댓글 조회", description = "특정 요청에 연관된 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<Comment>> findCommentsByRequestId(@PathVariable Long requestId) {
        List<Comment> comments = commentService.findCommentsByProjectId(requestId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "사용자 ID로 댓글 조회", description = "특정 사용자가 작성한 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자의 댓글 목록이 성공적으로 조회되었습니다.")
    @GetMapping("/account")
    public ResponseEntity<List<Comment>> findCommentsByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        List<Comment> comments = commentService.findCommentsByAccountId(accountId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글의 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "403", description = "댓글을 수정할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Comment> updateComment(
            @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        Comment updatedComment = commentService.updateComment(commentUpdateRequestDTO, accountId);
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(summary = "댓글 삭제", description = "ID를 사용하여 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "댓글이 성공적으로 삭제되었습니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
