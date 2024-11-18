package com.mtvs.devlinkbackend.email.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.email.model.dto.EmailRequestDTO;
import com.mtvs.devlinkbackend.email.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping
    public ResponseEntity<Map<String, String>> sendEmail(@RequestHeader("Authorization") String authHeader, @RequestBody EmailRequestDTO emailRequestDTO) throws Exception {
        String nickName = jwtUtil.getUserNickNameByToken(authHeader);
        emailService.sendEmail(emailRequestDTO, nickName);
        Map<String, String> response = new HashMap<>();
        response.put("isSuccess", "true");
        return ResponseEntity.ok(response);
    }

}
