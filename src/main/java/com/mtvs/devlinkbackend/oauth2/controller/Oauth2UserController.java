package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.dto.EpicGamesCallbackRequestDTO;
import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import com.mtvs.devlinkbackend.oauth2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class Oauth2UserController {

    private final EpicGamesTokenService epicGamesTokenService;
    private final UserService userService;

    public Oauth2UserController(EpicGamesTokenService epicGamesTokenService, UserService userService) {
        this.epicGamesTokenService = epicGamesTokenService;
        this.userService = userService;
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
            String token = extractToken(authorizationHeader);

            return ResponseEntity.ok(userService.findUserByAccessToken(token));
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

        try {
            Map<String, Object> userAccount =
                    epicGamesTokenService.getEpicGamesUserAccount(extractToken(authorizationHeader));

            return ResponseEntity.ok(userAccount);
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
            @RequestBody EpicGamesCallbackRequestDTO payload, HttpServletResponse response) {

        String code = payload.getCode();

        Map<String, Object> tokenBody = epicGamesTokenService.getAccessTokenAndRefreshTokenByCode(code);

        if (tokenBody != null) {
            String accessToken = (String) tokenBody.get("access_token");
            String refreshToken = (String) tokenBody.get("refresh_token");

            // accessToken과 refreshToken을 쿠키에 담아 return
            Cookie accessTokenCookie = new Cookie("access_token", "Bearer " + accessToken);
            accessTokenCookie.setMaxAge(15 * 60);
            accessTokenCookie.setSecure(true);
            Cookie refreshTokenCookie = new Cookie("refresh_token", "Bearer " + refreshToken);
            refreshTokenCookie.setMaxAge(3 * 60 * 60);
            refreshTokenCookie.setSecure(true);

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Authorization header must start with 'Bearer '");
        }
    }
}
