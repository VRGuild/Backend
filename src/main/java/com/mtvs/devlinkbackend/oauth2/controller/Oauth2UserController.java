package com.mtvs.devlinkbackend.oauth2.controller;

import com.mtvs.devlinkbackend.oauth2.service.UserService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.dto.request.EpicGamesCallbackRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/epicgames")
public class Oauth2UserController {

    private final EpicGamesTokenService epicGamesTokenService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public Oauth2UserController(EpicGamesTokenService epicGamesTokenService, JwtUtil jwtUtil, UserService userService) {
        this.epicGamesTokenService = epicGamesTokenService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    // epicgames 계정 정보 가져오는 API
    @GetMapping("/user-info")
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

    @GetMapping("/accountId")
    public ResponseEntity<?> getAccountId(
            @RequestHeader("Authorization") String authorizationHeader) {

        try {
            return ResponseEntity.ok(jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/callback")
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

    @GetMapping("/login")
    public ResponseEntity<?> authLogin(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {
        User user = userService.findUserByAuthorizationHeader(authorizationHeader);

        // 222 : 해당 User는 이미 서비스를 사용한 경험이 있음
        // 260 : 해당 User가 처음 서비스를 사용
        return user != null ? ResponseEntity.status(222).body("Existing User") : ResponseEntity.status(260).body("New User");
    }
}
