package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientGroupRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientGroup;
import com.mtvs.devlinkbackend.oauth2.service.UserClientGroupService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/group")
public class UserClientGroupController {
    private final UserClientGroupService userClientGroupService;
    private final JwtUtil jwtUtil;

    public UserClientGroupController(UserClientGroupService userClientGroupService, JwtUtil jwtUtil) {
        this.userClientGroupService = userClientGroupService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "UserClientGroup 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroup이 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserClientGroup> convertUserToUserClientGroup(
            @RequestBody UserClientGroupRequestDTO userClientGroupRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientGroup userClientGroup = 
                userClientGroupService.registUserClientGroup(userClientGroupRequestDTO, accountId);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "Authorization Header로 UserClientGroup 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroup을 조회함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserClientGroup> findUserClientGroupByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientGroup userClientGroup = userClientGroupService.findUserClientGroupByAccountId(accountId);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "담당자 이름에 특정 키워드가 포함된 UserClientGroups 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroups를 조회함")
    })
    @GetMapping("/manager-name/{managerName}")
    public ResponseEntity<List<UserClientGroup>> findByManagerNameContainingIgnoreCase(@PathVariable String managerName) {
        List<UserClientGroup> userClientGroups = userClientGroupService.findByManagerNameContainingIgnoreCase(managerName);
        return ResponseEntity.ok(userClientGroups);
    }

    @Operation(summary = "그룹 이름에 특정 키워드가 포함된 UserClientGroups 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroups를 조회함")
    })
    @GetMapping("/group-name/{groupName}")
    public ResponseEntity<List<UserClientGroup>> findByGroupNameContainingIgnoreCase(@PathVariable String groupName) {
        List<UserClientGroup> userClientGroups = userClientGroupService.findByGroupNameContainingIgnoreCase(groupName);
        return ResponseEntity.ok(userClientGroups);
    }

    @Operation(summary = "Client Type으로 UserClientGroups 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroups를 조회함")
    })
    @GetMapping("/client-type/{clientType}")
    public ResponseEntity<List<UserClientGroup>> findByClientType(@PathVariable String clientType) {
        List<UserClientGroup> userClientGroups = userClientGroupService.findByClientType(clientType);
        return ResponseEntity.ok(userClientGroups);
    }

    @Operation(summary = "담당자 전화번호로 UserClientGroups 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroups를 조회함")
    })
    @GetMapping("/manager-phone/{managerPhone}")
    public ResponseEntity<List<UserClientGroup>> findByManagerPhone(@PathVariable String managerPhone) {
        List<UserClientGroup> userClientGroups = userClientGroupService.findByManagerPhone(managerPhone);
        return ResponseEntity.ok(userClientGroups);
    }

    @Operation(summary = "UserClientGroup 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroup을 수정함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserClientGroup> updateUserClientGroup(
            @RequestBody UserClientGroupRequestDTO userClientGroupRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientGroup userClientGroup =
                userClientGroupService.updateUserClientGroup(userClientGroupRequestDTO, accountId);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "Authorization Header의 Account ID를 기반으로 UserClientGroup 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 UserClientGroup을 삭제함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUserClientGroupByAuthorizationHeader(@RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userClientGroupService.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}
