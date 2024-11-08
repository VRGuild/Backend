package com.mtvs.devlinkbackend.user.query.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.service.UserService;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserDetailSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserQueryController {
    private final UserViewService userViewService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public UserQueryController(UserViewService userViewService, JwtUtil jwtUtil, UserService userService) {
        this.userViewService = userViewService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Operation(summary = "UserId로 특정 사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserSingleResponseDTO> findDevByUserId(
            @PathVariable(name = "userId") Long userId) {

        UserSingleResponseDTO userSingleResponseDTO = userViewService.findUserByUserId(userId);
        return ResponseEntity.ok(userSingleResponseDTO);
    }

    @Operation(summary = "Epic Account Id로 특정 사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/epic")
    public ResponseEntity<UserSingleResponseDTO> findDevByEpicAccount(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        User user = userViewService.findUserByEpicAccountId(accountId);

        if(user == null) {
            user = userService.insertInitialUserInfo(accountId);
        }

        return ResponseEntity.ok(new UserSingleResponseDTO(user));
    }

    @Operation(summary = "UserId로 특정 사용자 상세 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/{userId}/member")
    public ResponseEntity<UserDetailSingleResponseDTO> findUserDetailByUserId(
            @PathVariable(name = "userId") Long userId) throws Exception {

        UserDetailSingleResponseDTO userDetailSingleResponseDTO = userViewService.findUserDetailByUserId(userId);
        return ResponseEntity.ok(userDetailSingleResponseDTO);
    }
}
