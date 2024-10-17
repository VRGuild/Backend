package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.util.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.component.EpicGamesJWKCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class EpicGamesTokenService {
    @Value("${epicgames.registration.client-id}")
    private String clientId;

    @Value("${epicgames.registration.client-secret}")
    private String clientSecret;

    @Value("${epicgames.registration.deployment-id}")
    private String deploymentId;

    private final String getAuthorizationCodeURL = "https://www.epicgames.com/id/authorize";
    private final String getAccessTokenURL = "https://api.epicgames.dev/epic/oauth/v2/token";
    private final String getAccountURL = "https://api.epicgames.dev/epic/id/v2/accounts?accountId=";

    private final EpicGamesJWKCache jwkCache;
    private final JwtUtil jwtUtil;

    // EpicGamesJWKCache를 주입받아 사용합니다.
    public EpicGamesTokenService(EpicGamesJWKCache jwkCache, JwtUtil jwtUtil) {
        this.jwkCache = jwkCache;
        this.jwtUtil = jwtUtil;
    }

    // 오프라인 JWT 검증 및 파싱 메서드
    public Map<String, Object> validateAuthHeaderAndParseToken(String authorizationHeader) throws Exception {
        // JWT 토큰 검증 및 파싱하여 Claims를 추출
        return jwtUtil.getClaimsFromAuthHeaderWithAuth(authorizationHeader);
    }

    public Map<String, Object> getAccessTokenAndRefreshTokenByCode(String code) {
        // Epic Games의 OAuth2 토큰 엔드포인트 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Basic Authentication 헤더 추가
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Authorization", authHeader);

        // 요청 본문 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("scope", "basic_profile friends_list presence");
        body.add("deployment_id", deploymentId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Epic Games 토큰 엔드포인트 요청
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                getAccessTokenURL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }

    public List<Map<String, Object>> getEpicGamesUserAccount(String authorizationHeader) throws Exception {
        // Epic Games의 OAuth2 토큰 엔드포인트 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Bearer Authentication 헤더 추가
        headers.set("Authorization", authorizationHeader);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

        // Epic Games 토큰 엔드포인트 요청
        ResponseEntity<List<Map<String, Object>>> response;

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        System.out.println(accountId);
        try {
            response = restTemplate.exchange(
                    getAccountURL + accountId,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청 중 오류 발생 : " + e.getMessage());
        }

        return response.getBody();
    }
}
