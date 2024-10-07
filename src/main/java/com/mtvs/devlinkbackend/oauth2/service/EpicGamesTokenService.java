package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.component.EpicGamesJWKCache;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EpicGamesTokenService {
    @Value("${spring.security.oauth2.client.registration.epicgames.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.epicgames.client-secret}")
    private String clientSecret;

    private final String redirectUri = "";

    private final EpicGamesJWKCache jwkCache;
    private final JwtUtil jwtUtil;

    // EpicGamesJWKCache를 주입받아 사용합니다.
    public EpicGamesTokenService(EpicGamesJWKCache jwkCache, JwtUtil jwtUtil) {
        this.jwkCache = jwkCache;
        this.jwtUtil = jwtUtil;
    }

    // 오프라인 JWT 검증 및 파싱 메서드
    public Map<String, Object> validateAndParseToken(String token) throws Exception {
        // JWT 토큰 검증 및 파싱하여 Claims를 추출
        return jwtUtil.getClaimsFromToken(token);
    }

    public Map<String, Object> getAccessTokenAndRefreshToken(String code) {
        // Epic Games의 OAuth2 토큰 엔드포인트 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Basic Authentication 헤더 추가
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", authHeader);

        // 요청 본문 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Epic Games 토큰 엔드포인트 요청
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.epicgames.dev/epic/oauth/v2/token",
                HttpMethod.POST,
                request,
                Map.class
        );

        return response.getBody();
    }

    public Map<String, Object> getEpicGamesUserAccount(String accessToken) {
        // Epic Games의 OAuth2 토큰 엔드포인트 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Bearer Authentication 헤더 추가
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        // Epic Games 토큰 엔드포인트 요청
        ResponseEntity<Map> response;

        try {
            response = restTemplate.exchange(
                    "https://api.epicgames.dev/epic/id/v2/accounts?accountId=" + jwtUtil.getClaimsFromToken(accessToken),
                    HttpMethod.GET,
                    request,
                    Map.class
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response.getBody();
    }
}
