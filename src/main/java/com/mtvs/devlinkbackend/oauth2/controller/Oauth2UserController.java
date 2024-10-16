package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.dto.EpicGamesCallbackRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import com.mtvs.devlinkbackend.oauth2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class Oauth2UserController {

    private final EpicGamesTokenService epicGamesTokenService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public Oauth2UserController(EpicGamesTokenService epicGamesTokenService, UserService userService, JwtUtil jwtUtil) {
        this.epicGamesTokenService = epicGamesTokenService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 로컬 user 정보 가져오는 API
    @GetMapping("/local/user-info")
    @Operation(
            summary = "로컬 유저 정보 조회",
            description = "DevLink만의 DB에 저장된 유저 정보를 조회한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<?> getLocalUserInfo(@RequestHeader("Authorization") String authorizationHeader) {

        try {
            return ResponseEntity.ok(userService.findUserByAuthorizationHeader(authorizationHeader));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // epicgames 계정 정보 가져오는 API
    @GetMapping("/epicgames/user-info")
    @Operation(
            summary = "EpicGames 유저 정보 조회",
            description = "EpicGames의 유저 정보를 조회한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<?> getEpicGamesUserInfo(
            @RequestHeader("Authorization") String authorizationHeader) {

        System.out.println(authorizationHeader);

        try {
            List<Map<String, Object>> userAccount =
                    epicGamesTokenService.getEpicGamesUserAccount(authorizationHeader);

            return ResponseEntity.ok(userAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @GetMapping("/epicgames/accountId")
    public ResponseEntity<?> getAccountId(
            @RequestHeader("Authorization") String authorizationHeader) {

        try {
            return ResponseEntity.ok(jwtUtil.getSubjectFromAuthHeaderWithAuth(authorizationHeader));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/epicgames/callback")
    @Operation(
            summary = "EpicGames AccessToken 요청",
            description = "EpicGames로부터 사용자에게 AccessToken을 전달한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "AccessToken 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 헤더 또는 파라미터 전달"),
    })
    public ResponseEntity<?> handleEpicGamesCallback(
            @RequestBody EpicGamesCallbackRequestDTO payload) {

        String code = payload.getCode();

        Map<String, Object> tokenBody = epicGamesTokenService.getAccessTokenAndRefreshTokenByCode(code);

        if (tokenBody != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenBody);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authLogin(
            @RequestHeader(name = "Authorization") String authorizationHeader) {
        User user = userService.findUserByAuthorizationHeader(authorizationHeader);

        // 222 : 해당 User는 이미 서비스를 사용한 경험이 있음
        // 260 : 해당 User가 처음 서비스를 사용
        return user != null ? ResponseEntity.status(222).body("Existing User") : ResponseEntity.status(260).body("New User");
    }

    @PatchMapping("/local/user-info")
    public ResponseEntity<?> updateLocalUserInfo() {
        // User 추가 정보 확정되면 개발 예정
        return ResponseEntity.ok().build();
    }
}
