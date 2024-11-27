package com.mtvs.devlinkbackend.email.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.email.model.dto.request.EmailRequestDTO;
import com.mtvs.devlinkbackend.email.model.dto.response.EmailResponseDTO;
import com.mtvs.devlinkbackend.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    public EmailController(JwtUtil jwtUtil, EmailService emailService) {
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Operation(summary = "Memo 조회", description = "채널에 대한 메모들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memo가 성공적으로 조회되었습니다.",
                    content = @Content(schema = @Schema(implementation = EmailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmailResponseDTO> sendEmail(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmailRequestDTO emailRequestDTO) throws Exception {
        String nickName = jwtUtil.getUserNickNameByToken(authHeader);
        EmailResponseDTO emailResponseDTO = new EmailResponseDTO();
        if(emailService.sendEmail(emailRequestDTO, nickName))
            emailResponseDTO.setIsSuccess(Boolean.TRUE);
        else emailResponseDTO.setIsSuccess(Boolean.FALSE);
        return ResponseEntity.ok(emailResponseDTO);
    }

}
