package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientIndividualConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientIndividual;
import com.mtvs.devlinkbackend.oauth2.service.UserClientIndividualService;
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

    public UserClientIndividualController(UserClientIndividualService userClientIndividualService) {
        this.userClientIndividualService = userClientIndividualService;
    }

    @Operation(summary = "UserClientIndividual로 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual이 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserClientIndividual> convertUserToUserClientIndividual(
            @RequestBody UserClientIndividualConvertRequestDTO userClientIndividualConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        UserClientIndividual userClientIndividual =
                userClientIndividualService.registUserClientIndividual(
                        userClientIndividualConvertRequestDTO, authorizationHeader);
        return ResponseEntity.ok(userClientIndividual);
    }

    @Operation(summary = "Authorization Header로 UserClientIndividual 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual을 조회함"),
            @ApiResponse(responseCode = "404", description = "UserClientIndividual을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserClientIndividual> findUserClientIndividualByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        UserClientIndividual userClientIndividual =
                userClientIndividualService.findUserClientIndividualByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(userClientIndividual);
    }

    @Operation(summary = "이름에 특정 키워드가 포함된 UserClientIndividuals 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividuals를 조회함")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserClientIndividual>> findUserClientIndividualsByNameContainingIgnoreCase(@PathVariable String name) {
        List<UserClientIndividual> userClientIndividuals =
                userClientIndividualService.findUserClientIndividualsByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(userClientIndividuals);
    }

    @Operation(summary = "전화번호로 UserClientIndividuals 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividuals를 조회함")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<List<UserClientIndividual>> findUserClientIndividualsByPhone(@PathVariable String phone) {
        List<UserClientIndividual> userClientIndividuals =
                userClientIndividualService.findUserClientIndividualsByPhone(phone);
        return ResponseEntity.ok(userClientIndividuals);
    }

    @Operation(summary = "UserClientIndividual 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientIndividual을 수정함"),
            @ApiResponse(responseCode = "404", description = "UserClientIndividual을 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserClientIndividual> updateUserClientIndividual(
            @RequestBody UserClientIndividualConvertRequestDTO userClientIndividualConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        UserClientIndividual userClientIndividual =
                userClientIndividualService.updateUserClientIndividual(
                        userClientIndividualConvertRequestDTO, authorizationHeader);
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

        userClientIndividualService.deleteByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
