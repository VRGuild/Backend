package com.mtvs.devlinkbackend.oauth2.component;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class EpicGamesJWKCache {

    private static final String JWKS_URL = "https://api.epicgames.dev/epic/oauth/v2/.well-known/jwks.json";

    private JWKSet jwkSet;

    private final ReentrantLock lock = new ReentrantLock();

    public EpicGamesJWKCache() throws Exception {
        // 초기 로드: 애플리케이션이 시작될 때 한 번 로드
        loadJWKs();
    }

    // 1시간에 한 번 공개 키를 갱신하는 스케줄러
    @Scheduled(cron = "0 0 * * * ?")  // 매일 자정에 실행
    public void refreshJWKs() {
        try {
            loadJWKs();
            System.out.println("JWKs have been refreshed successfully.");
        } catch (Exception e) {
            System.err.println("Failed to refresh JWKs: " + e.getMessage());
        }
    }

    // JWK를 Epic Games로부터 가져오는 메서드
    private void loadJWKs() throws Exception {
        lock.lock();
        try {
            URL jwksUrl = new URL(JWKS_URL);
            jwkSet = JWKSet.load(jwksUrl);
        } finally {
            lock.unlock();
        }
    }

    // 캐싱된 JWK를 반환하는 메서드
    public JWKSet getCachedJWKSet() {
        return jwkSet;
    }
}
