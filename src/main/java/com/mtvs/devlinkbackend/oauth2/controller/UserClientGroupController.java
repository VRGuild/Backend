package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientGroupConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientGroup;
import com.mtvs.devlinkbackend.oauth2.service.UserClientGroupService;
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

    public UserClientGroupController(UserClientGroupService userClientGroupService) {
        this.userClientGroupService = userClientGroupService;
    }

    @Operation(summary = "Authorization Header로 UserClientGroup 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroup을 조회함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<UserClientGroup> findUserClientGroupByAuthorizationHeader(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        UserClientGroup userClientGroup = userClientGroupService.findUserClientGroupByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "관리자 이름에 특정 키워드가 포함된 UserClientGroups 조회")
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

    @Operation(summary = "관리자 전화번호로 UserClientGroups 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroups를 조회함")
    })
    @GetMapping("/manager-phone/{managerPhone}")
    public ResponseEntity<List<UserClientGroup>> findByManagerPhone(@PathVariable String managerPhone) {
        List<UserClientGroup> userClientGroups = userClientGroupService.findByManagerPhone(managerPhone);
        return ResponseEntity.ok(userClientGroups);
    }

    @Operation(summary = "User를 UserClientGroup으로 변환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 User를 UserClientGroup으로 변환함"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping
    public ResponseEntity<UserClientGroup> convertUserToUserClientGroup(
            @RequestBody UserClientGroupConvertRequestDTO userClientGroupConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        UserClientGroup userClientGroup = userClientGroupService.converUserToUserClientGroup(userClientGroupConvertRequestDTO, authorizationHeader);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "UserClientGroup 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 UserClientGroup을 수정함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<UserClientGroup> updateUserClientGroup(
            @RequestBody UserClientGroupConvertRequestDTO userClientGroupConvertRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        UserClientGroup userClientGroup = userClientGroupService.updateUserClientGroup(userClientGroupConvertRequestDTO, authorizationHeader);
        return ResponseEntity.ok(userClientGroup);
    }

    @Operation(summary = "Authorization Header의 Account ID를 기반으로 UserClientGroup 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 UserClientGroup을 삭제함"),
            @ApiResponse(responseCode = "404", description = "UserClientGroup을 찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUserClientGroupByAuthorizationHeader(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        userClientGroupService.deleteByAuthorizationHeader(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
