package com.mtvs.devlinkbackend.config;

import com.mtvs.devlinkbackend.oauth2.service.EpicGamesTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final EpicGamesTokenService epicGamesTokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, EpicGamesTokenService epicGamesTokenService) {
        this.jwtUtil = jwtUtil;
        this.epicGamesTokenService = epicGamesTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("JwtAuthenticationFilter 실행됨: " + request.getRequestURI());

        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        // 헤더에서 액세스 토큰 추출
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분 추출
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 잘못된 인증 헤더
            return;
        }

        try {
            // 토큰 검증 | 검증 성공 시 SecurityContext에 인증 정보 저장
            String userPrincipal = jwtUtil.getSubjectFromTokenWithAuth(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userPrincipal, null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // 검증 실패 시 401 에러 설정
            if(e.getMessage().equals("JWT is expired"))
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            else
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 필터 체인 진행
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Swagger 관련 모든 경로 예외 처리
        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")
                || path.startsWith("/api");
    }

    // 쿠키에서 리프레시 토큰을 추출하는 메서드
    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
