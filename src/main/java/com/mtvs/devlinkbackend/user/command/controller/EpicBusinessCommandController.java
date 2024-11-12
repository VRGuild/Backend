package com.mtvs.devlinkbackend.user.command.controller;

import com.mtvs.devlinkbackend.user.command.model.dto.request.BusinessRequestDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.BusinessSingleResponseDTO;
import com.mtvs.devlinkbackend.user.command.service.EpicBusinessService;
import com.mtvs.devlinkbackend.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/epic/businesses")
public class EpicBusinessCommandController {
    private final EpicBusinessService epicBusinessService;
    private final JwtUtil jwtUtil;

    public EpicBusinessCommandController(EpicBusinessService epicBusinessService, JwtUtil jwtUtil) {
        this.epicBusinessService = epicBusinessService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Epic 계정으로 Business 계정 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<BusinessSingleResponseDTO> registerBusiness(
            @Parameter(content = @Content(mediaType = "multipart/form-data"))
            @ModelAttribute BusinessRequestDTO businessRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        BusinessSingleResponseDTO userClientGroup =
                epicBusinessService.registUserClientGroup(businessRequestDTO, accountId);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "Epic 계정으로 Business 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<BusinessSingleResponseDTO> update(
            @RequestBody BusinessRequestDTO businessRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        BusinessSingleResponseDTO userClientGroup =
                epicBusinessService.updateUserClientGroup(businessRequestDTO, accountId);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "Authorization Header의 Epic Account ID를 기반으로 Business 계정 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제함"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteByAuthorizationHeader(@RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        epicBusinessService.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}
