package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.UserPartnerConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import com.mtvs.devlinkbackend.oauth2.service.UserPartnerService;
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

    public UserPartnerController(UserPartnerService userPartnerService) {
        this.userPartnerService = userPartnerService;
    }

    @Operation(summary = "Account ID로 UserPartner 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 조회함"),
            @ApiResponse(responseCode = "404", description = "UserPartner를 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserPartner> findUserPartnerByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        UserPartner userPartner = userPartnerService.findUserPartnerByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "이름에 특정 키워드가 포함된 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserPartner>> findUserPartnersByNameContainingIgnoreCase(@PathVariable String name) {
        List<UserPartner> userPartners = userPartnerService.findUserPartnersByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "닉네임에 특정 키워드가 포함된 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<List<UserPartner>> findUserPartnersByNicknameContainingIgnoreCase(@PathVariable String nickname) {
        List<UserPartner> userPartners = userPartnerService.findUserPartnersByNicknameContainingIgnoreCase(nickname);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "이메일로 UserPartners 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartners를 조회함")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserPartner>> findUserPartnersByEmail(@PathVariable String email) {
        List<UserPartner> userPartners = userPartnerService.findUserPartnersByEmail(email);
        return ResponseEntity.ok(userPartners);
    }

    @Operation(summary = "전화번호로 UserPartner 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 조회함")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<UserPartner> findUserPartnerByPhone(@PathVariable String phone) {
        UserPartner userPartner = userPartnerService.findUserPartnerByPhone(phone);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "User를 UserPartner로 변환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 User를 UserPartner로 변환함"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserPartner> convertUserToUserPartner(
            @RequestBody UserPartnerConvertRequestDTO userPartnerConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        UserPartner userPartner = userPartnerService.converUserToUserPartner(userPartnerConvertRequestDTO, authorizationHeader);
        return ResponseEntity.ok(userPartner);
    }

    @Operation(summary = "UserPartner 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserPartner를 수정함"),
            @ApiResponse(responseCode = "404", description = "UserPartner를 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserPartner> updateUserPartner(
            @RequestBody UserPartnerConvertRequestDTO userPartnerConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        UserPartner userPartner = userPartnerService.updateUserPartner(userPartnerConvertRequestDTO, authorizationHeader);
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
        userPartnerService.deleteByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
