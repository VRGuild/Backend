package com.mtvs.devlinkbackend.user.command.controller;

import com.mtvs.devlinkbackend.user.command.model.dto.request.DevRegistRequestDTO;
import com.mtvs.devlinkbackend.user.command.model.dto.request.DevUpdateRequestDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.DevSingleResponseDTO;
import com.mtvs.devlinkbackend.user.command.service.EpicDevService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/epic/developer")
public class EpicDevCommandController {
    private final EpicDevService epicDevService;
    private final JwtUtil jwtUtil;

    public EpicDevCommandController(EpicDevService epicDevService, JwtUtil jwtUtil) {
        this.epicDevService = epicDevService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Epic 계정으로 Dev 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<DevSingleResponseDTO> registerDev(
            @RequestBody DevRegistRequestDTO devRegistRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        DevSingleResponseDTO userPartner = epicDevService.registDev(devRegistRequestDTO, accountId);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "Epic 계정으로 Dev 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<DevSingleResponseDTO> update(
            @RequestBody DevUpdateRequestDTO devUpdateRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        DevSingleResponseDTO userPartner = epicDevService.updateUserPartner(devUpdateRequestDTO, accountId);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "Authorization Header의 Account ID를 기반으로 UserPartner 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        epicDevService.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}
