package com.mtvs.devlinkbackend.comment.controller;

import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.service.CommentService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentCommandController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentCommandController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "댓글 등록", description = "특정 요청에 대한 새로운 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 요청을 찾을 수 없습니다.")
    })
    @PostMapping
    public ResponseEntity<CommentSingleResponseDTO> registComment(
            @RequestBody CommentRegistRequestDTO commentRegistRequestDTO) {

        CommentSingleResponseDTO comment = commentService.registComment(commentRegistRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글의 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "403", description = "댓글을 수정할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<CommentSingleResponseDTO> updateComment(
            @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        CommentSingleResponseDTO updatedComment = commentService.updateComment(commentUpdateRequestDTO, accountId);
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
