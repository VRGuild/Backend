package com.mtvs.devlinkbackend.ether.controller;

import com.mtvs.devlinkbackend.ether.dto.response.EtherSingleResponseDTO;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.ether.dto.request.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.request.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.service.EtherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ether")
public class EtherCommandController {
    private final EtherService etherService;
    private final JwtUtil jwtUtil;

    public EtherCommandController(EtherService etherService, JwtUtil jwtUtil) {
        this.etherService = etherService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "새 에테르 이력 등록", description = "새 에테르 이력를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "에테르가 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PostMapping
    public ResponseEntity<EtherSingleResponseDTO> registEther(
            @RequestBody EtherRegistRequestDTO etherRegistRequestDTO) {

        EtherSingleResponseDTO newEther = etherService.registEther(etherRegistRequestDTO);
        return ResponseEntity.ok(newEther);
    }

    @Operation(summary = "Ether 수정", description = "제공된 데이터를 기반으로 특정 Ether를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether가 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 수정 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PatchMapping
    public ResponseEntity<EtherSingleResponseDTO> updateEther(
            @RequestBody EtherUpdateRequestDTO etherUpdateRequestDTO) {
        try {
            EtherSingleResponseDTO updatedEther = etherService.updateEther(etherUpdateRequestDTO);
            return ResponseEntity.ok(updatedEther);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Ether 삭제", description = "Ether ID를 사용하여 Ether를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ether가 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "Ether를 찾을 수 없음")
    })
    @DeleteMapping("/{etherId}")
    public ResponseEntity<Void> deleteEtherByEtherId(@PathVariable Long etherId) {
        etherService.deleteEtherByEtherId(etherId);
        return ResponseEntity.noContent().build();
    }
}
