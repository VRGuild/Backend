package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import com.mtvs.devlinkbackend.oauth2.service.UserService;
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

    // 로컬에 저장된 user 정보 가져오는 API
    @GetMapping("/local/user-info")
    public ResponseEntity<?> getLocalUserInfo(@RequestHeader("Authorization") String accessToken) {
        try {
            // JWT 토큰에서 사용자 정보 추출
            Map<String, Object> claims = epicGamesTokenService.validateAndParseToken(extractToken(accessToken));

            if(userService.findUserByAccessToken(extractToken(accessToken)) != null) {
                userService.registUserByAccessToken(extractToken(accessToken));
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // epicgames 계정 정보 가져오는 API
    @GetMapping("/epicgames/user-info")
    public ResponseEntity<?> getEpicGamesUserInfo(
            @RequestHeader("Authorization") String accessToken) {

        try {
            Map<String, Object> userAccount =
                    epicGamesTokenService.getEpicGamesUserAccount(extractToken(accessToken));

            return ResponseEntity.ok(userAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/epicgames/callback")
    public ResponseEntity<?> handleEpicGamesCallback(
            @RequestBody Map<String, String> payload, HttpServletResponse response) {

        String code = payload.get("code");

        Map<String, Object> tokenBody = epicGamesTokenService.getAccessTokenAndRefreshToken(code);

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

            return ResponseEntity.ok().build();
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
