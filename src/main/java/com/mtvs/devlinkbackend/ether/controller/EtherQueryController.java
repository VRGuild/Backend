package com.mtvs.devlinkbackend.ether.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.ether.dto.response.EtherPagingResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.EtherSingleResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.response.UserEtherAmountResponseDTO;
import com.mtvs.devlinkbackend.ether.service.EtherViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ether")
public class EtherQueryController {

    private final EtherViewService etherViewService;
    private final JwtUtil jwtUtil;

    public EtherQueryController(EtherViewService etherViewService, JwtUtil jwtUtil) {
        this.etherViewService = etherViewService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Ether ID로 Ether 조회", description = "Ether ID를 사용하여 Ether를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether를 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "Ether를 찾을 수 없음")
    })
    @GetMapping("/{etherId}")
    public ResponseEntity<EtherSingleResponseDTO> findEtherByEtherId(@PathVariable Long etherId) {
        EtherSingleResponseDTO ether = etherViewService.findEtherByEtherId(etherId);
        return ether != null ? ResponseEntity.ok(ether) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "계정 ID로 Ether 목록 조회", description = "계정 ID를 사용하여 관련된 모든 Ether를 15개씩 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether 목록을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/list/{page}")
    public ResponseEntity<EtherPagingResponseDTO> findEthersByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Integer page) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        EtherPagingResponseDTO ethers = etherViewService.findEthersByAccountIdWithPagination(accountId, page);
        return ResponseEntity.ok(ethers);
    }

    @Operation(summary = "사용자의 총 Ether 양 조회", description = "사용자의 총 Ether 양을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether 목록을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/history/{userId}")
    public ResponseEntity<UserEtherAmountResponseDTO> findTotalEtherAmountByUserId(@PathVariable Long userId) {
        UserEtherAmountResponseDTO ethers = etherViewService.findTotalEtherAmountByUserId(userId);
        return ResponseEntity.ok(ethers);
    }
}
