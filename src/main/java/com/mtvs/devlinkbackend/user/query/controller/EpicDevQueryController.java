package com.mtvs.devlinkbackend.user.query.controller;

import com.mtvs.devlinkbackend.user.query.model.dto.response.DevPagingResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.DevSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.service.EpicDevViewService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/epic/developer")
public class EpicDevQueryController {
    private final JwtUtil jwtUtil;
    private final EpicDevViewService epicDevViewService;

    public EpicDevQueryController(JwtUtil jwtUtil, EpicDevViewService epicDevViewService) {
        this.jwtUtil = jwtUtil;
        this.epicDevViewService = epicDevViewService;
    }

    @Operation(summary = "Epic 계정으로 자기 Dev 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping
    public ResponseEntity<DevSingleResponseDTO> findDevByEpicAccount(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        DevSingleResponseDTO devSingleResponseDTO = epicDevViewService.findDevByEpicAccountId(accountId);
        return ResponseEntity.ok(devSingleResponseDTO);
    }

    @Operation(summary = "Epic 계정으로 상대 Dev 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<DevSingleResponseDTO> findDevByUserId(
            @PathVariable(name = "userId") Long userId) throws Exception {

        DevSingleResponseDTO devSingleResponseDTO = epicDevViewService.findDevByUserId(userId);
        return ResponseEntity.ok(devSingleResponseDTO);
    }

    @Operation(summary = "Epic 계정으로 Dev 전체 Pagination 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/all")
    public ResponseEntity<DevPagingResponseDTO> findDevByEpicAccount(
            @RequestParam(name = "page") int page) throws Exception {

        DevPagingResponseDTO devPagingResponseDTO = epicDevViewService.findAllDevsWithPagination(page);
        return ResponseEntity.ok(devPagingResponseDTO);
    }
}
