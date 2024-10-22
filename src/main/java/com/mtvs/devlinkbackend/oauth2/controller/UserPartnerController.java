package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserPartnerRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserPartnerSingleResponseDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import com.mtvs.devlinkbackend.oauth2.service.UserPartnerService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/partner")
public class UserPartnerController {
    private final UserPartnerService userPartnerService;
    private final JwtUtil jwtUtil;

    public UserPartnerController(UserPartnerService userPartnerService, JwtUtil jwtUtil) {
        this.userPartnerService = userPartnerService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "UserPartner 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserPartnerSingleResponseDTO> convertUserToUserPartner(
            @RequestBody UserPartnerRequestDTO userPartnerRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserPartnerSingleResponseDTO userPartner = userPartnerService.registUserPartner(userPartnerRequestDTO, accountId);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "Account ID로 UserPartner 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 조회함"),
            @ApiResponse(responseCode = "404", description = "UserPartner를 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserPartnerSingleResponseDTO> findUserPartnerByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserPartnerSingleResponseDTO userPartner = userPartnerService.findUserPartnerByAccountId(accountId);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "이름에 특정 키워드가 포함된 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<UserPartnerListResponseDTO> findUserPartnersByNameContainingIgnoreCase(@PathVariable String name) {
        UserPartnerListResponseDTO userPartners = userPartnerService.findUserPartnersByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "닉네임에 특정 키워드가 포함된 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UserPartnerListResponseDTO> findUserPartnersByNicknameContainingIgnoreCase(@PathVariable String nickname) {
        UserPartnerListResponseDTO userPartners = userPartnerService.findUserPartnersByNicknameContainingIgnoreCase(nickname);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "이메일로 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserPartnerListResponseDTO> findUserPartnersByEmail(@PathVariable String email) {
        UserPartnerListResponseDTO userPartners = userPartnerService.findUserPartnersByEmail(email);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "전화번호로 UserPartner 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 조회함")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<UserPartnerSingleResponseDTO> findUserPartnerByPhone(@PathVariable String phone) {
        UserPartnerSingleResponseDTO userPartner = userPartnerService.findUserPartnerByPhone(phone);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "UserPartner 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 수정함"),
            @ApiResponse(responseCode = "404", description = "UserPartner를 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserPartnerSingleResponseDTO> updateUserPartner(
            @RequestBody UserPartnerRequestDTO userPartnerRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserPartnerSingleResponseDTO userPartner = userPartnerService.updateUserPartner(userPartnerRequestDTO, accountId);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "Authorization Header의 Account ID를 기반으로 UserPartner 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 UserPartner를 삭제함"),
            @ApiResponse(responseCode = "404", description = "UserPartner를 찾을 수 없음")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserPartnerByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userPartnerService.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}
