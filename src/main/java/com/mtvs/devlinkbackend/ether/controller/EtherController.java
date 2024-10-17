package com.mtvs.devlinkbackend.ether.controller;

import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.ether.dto.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.service.EtherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ether")
public class EtherController {
    private final EtherService etherService;
    private final JwtUtil jwtUtil;

    public EtherController(EtherService etherService, JwtUtil jwtUtil) {
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
    public ResponseEntity<Ether> registEther(
            @RequestBody EtherRegistRequestDTO etherRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        Ether newEther = etherService.registEther(etherRegistRequestDTO, accountId);
        return ResponseEntity.ok(newEther);
    }

    @Operation(summary = "Ether ID로 Ether 조회", description = "Ether ID를 사용하여 Ether를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether를 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "Ether를 찾을 수 없음")
    })
    @GetMapping("/{etherId}")
    public ResponseEntity<Ether> findEtherByEtherId(@PathVariable Long etherId) {
        Ether ether = etherService.findEtherByEtherId(etherId);
        return ether != null ? ResponseEntity.ok(ether) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "계정 ID로 Ether 목록 조회", description = "계정 ID를 사용하여 관련된 모든 Ether를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether 목록을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/account")
    public ResponseEntity<List<Ether>> findEthersByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        List<Ether> ethers = etherService.findEthersByAccountId(accountId);
        return ResponseEntity.ok(ethers);
    }

    @Operation(summary = "이유로 Ether 목록 조회", description = "지정된 이유로 관련된 모든 Ether를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether 목록을 성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/reason/{reason}")
    public ResponseEntity<List<Ether>> findEthersByReason(@PathVariable String reason) {
        List<Ether> ethers = etherService.findEthersByReason(reason);
        return ResponseEntity.ok(ethers);
    }

    @Operation(summary = "Ether 수정", description = "제공된 데이터를 기반으로 특정 Ether를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ether가 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 수정 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PatchMapping
    public ResponseEntity<Ether> updateEther(@RequestBody EtherUpdateRequestDTO etherUpdateRequestDTO) {
        try {
            Ether updatedEther = etherService.updateEther(etherUpdateRequestDTO);
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
