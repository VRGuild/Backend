package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserClientIndividualRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientIndividualListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientIndividualSingleResponseDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientIndividual;
import com.mtvs.devlinkbackend.oauth2.service.UserClientIndividualService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/individual")
public class UserClientIndividualController {
    private final UserClientIndividualService userClientIndividualService;
    private final JwtUtil jwtUtil;

    public UserClientIndividualController(UserClientIndividualService userClientIndividualService, JwtUtil jwtUtil) {
        this.userClientIndividualService = userClientIndividualService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "UserClientIndividual로 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual이 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserClientIndividualSingleResponseDTO> convertUserToUserClientIndividual(
            @RequestBody UserClientIndividualRequestDTO userClientIndividualRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientIndividualSingleResponseDTO userClientIndividual =
                userClientIndividualService.registUserClientIndividual(
                        userClientIndividualRequestDTO, accountId);
        return ResponseEntity.ok(userClientIndividual);
    }

    @Operation(summary = "Authorization Header로 UserClientIndividual 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual을 조회함"),
            @ApiResponse(responseCode = "404", description = "UserClientIndividual을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserClientIndividualSingleResponseDTO> findUserClientIndividualByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientIndividualSingleResponseDTO userClientIndividual =
                userClientIndividualService.findUserClientIndividualByAccountId(accountId);
        return ResponseEntity.ok(userClientIndividual);
    }

    @Operation(summary = "이름에 특정 키워드가 포함된 UserClientIndividuals 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividuals를 조회함")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<UserClientIndividualListResponseDTO> findUserClientIndividualsByNameContainingIgnoreCase(@PathVariable String name) {
        UserClientIndividualListResponseDTO userClientIndividuals =
                userClientIndividualService.findUserClientIndividualsByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(userClientIndividuals);
    }

    @Operation(summary = "전화번호로 UserClientIndividuals 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividuals를 조회함")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<UserClientIndividualListResponseDTO> findUserClientIndividualsByPhone(@PathVariable String phone) {
        UserClientIndividualListResponseDTO userClientIndividuals =
                userClientIndividualService.findUserClientIndividualsByPhone(phone);
        return ResponseEntity.ok(userClientIndividuals);
    }

    @Operation(summary = "UserClientIndividual 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual을 수정함"),
            @ApiResponse(responseCode = "404", description = "UserClientIndividual을 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserClientIndividualSingleResponseDTO> updateUserClientIndividual(
            @RequestBody UserClientIndividualRequestDTO userClientIndividualRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientIndividualSingleResponseDTO userClientIndividual =
                userClientIndividualService.updateUserClientIndividual(
                        userClientIndividualRequestDTO, accountId);
        return ResponseEntity.ok(userClientIndividual);
    }

    @Operation(summary = "Authorization Header의 Account ID를 기반으로 UserClientIndividual 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 UserClientIndividual을 삭제함"),
            @ApiResponse(responseCode = "404", description = "UserClientIndividual을 찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUserClientIndividualByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userClientIndividualService.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}
