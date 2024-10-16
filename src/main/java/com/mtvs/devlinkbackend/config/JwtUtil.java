package com.mtvs.devlinkbackend.config;

import com.mtvs.devlinkbackend.oauth2.component.EpicGamesJWKCache;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {

    private static final String ISSUER_URL = "https://api.epicgames.dev";

    @Value("${spring.security.oauth2.client.registration.epicgames.client-id}")
    private String clientId;

    private final EpicGamesJWKCache jwkCache;

    public JwtUtil(EpicGamesJWKCache jwkCache) {
        this.jwkCache = jwkCache;
    }

    // JWT 서명 및 검증을 통한 Claims 추출
    public Map<String, Object> getClaimsFromAuthHeaderWithAuth(String authorizationHeader) throws Exception {
        // Claims 검증
        JWTClaimsSet claims = getClaimsFromToken(extractToken(authorizationHeader));
        validateClaims(claims);

        // 검증이 완료되었을 경우 모든 Claims을 Map으로 변환하여 반환
        return convertClaimsToMap(claims);
    }

    // JWT Claims 추출
    public Map<String, Object> getClaimsFromAuthHeaderWithoutAuth(String authorizationHeader) throws Exception {
        // Claims 검증
        JWTClaimsSet claims = getClaimsFromToken(extractToken(authorizationHeader));

        // 검증이 완료되었을 경우 모든 Claims을 Map으로 변환하여 반환
        return convertClaimsToMap(claims);
    }

    // 검증된 'sub' 값, accountId 반환
    public String getSubjectFromAuthHeaderWithAuth(String authorizationHeader) throws Exception {
        Map<String, Object> claims = getClaimsFromAuthHeaderWithAuth(authorizationHeader);
        return (String) claims.get("sub");
    }

    // 검증되지 않은 'sub' 값, accountId 반환
    public String getSubjectFromAuthHeaderWithoutAuth(String authorizationHeader) throws Exception {
        Map<String, Object> claims = getClaimsFromAuthHeaderWithoutAuth(authorizationHeader);
        return (String) claims.get("sub");
    }

    private void validateClaims(JWTClaimsSet claims) throws BadJWTException {
        // 'iss' 검증
        if (claims.getIssuer() == null || !claims.getIssuer().startsWith(ISSUER_URL)) {
            throw new BadJWTException("Invalid issuer");
        }

        // 'iat' 검증
        Date now = new Date();
        if (claims.getIssueTime() == null || claims.getIssueTime().after(now)) {
            throw new BadJWTException("Invalid issue time");
        }

        // 'exp' 검증
        if (claims.getExpirationTime() == null || claims.getExpirationTime().before(now)) {
            throw new BadJWTException("JWT is expired");
        }

        // 'aud' 검증
        List<String> audience = claims.getAudience();
        if (audience == null || !audience.contains(clientId)) {
            throw new BadJWTException("Invalid audience");
        }
    }

    private JWTClaimsSet getClaimsFromToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWK jwk = jwkCache.getCachedJWKSet().getKeyByKeyId(signedJWT.getHeader().getKeyID());

        if (jwk == null || !JWSAlgorithm.RS256.equals(signedJWT.getHeader().getAlgorithm())) {
            throw new RuntimeException("JWK key is missing or invalid algorithm");
        }

        JWSVerifier verifier = new RSASSAVerifier(jwk.toRSAKey());
        if (!signedJWT.verify(verifier)) {
            throw new RuntimeException("JWT signature verification failed");
        }

        return signedJWT.getJWTClaimsSet();
    }

    // Claims를 Map<String, Object> 형식으로 변환하는 메서드
    private Map<String, Object> convertClaimsToMap(JWTClaimsSet claims) {
        Map<String, Object> claimsMap = new HashMap<>();
        claims.getClaims().forEach(claimsMap::put);
        return claimsMap;
    }

    private String extractToken(String authorizationHeader) {
        System.out.println(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Authorization header must start with 'Bearer '");
        }
    }
}
