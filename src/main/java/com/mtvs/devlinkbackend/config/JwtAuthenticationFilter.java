package com.mtvs.devlinkbackend.config;

import com.mtvs.devlinkbackend.util.JwtUtil;
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

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("JwtAuthenticationFilter 실행됨: " + request.getRequestURI());

        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        try {
            // 토큰 검증 | 검증 성공 시 SecurityContext에 인증 정보 저장
            String accountId = jwtUtil.getSubjectFromAuthHeaderWithAuth(authorizationHeader);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(accountId, null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // 검증 실패 시 401 에러 설정
            if(e.getMessage().equals("JWT is expired"))
                response.setStatus(449);
            else
                System.out.println(e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 헤더에 들어 있는 토큰이 잘못됨
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
}
