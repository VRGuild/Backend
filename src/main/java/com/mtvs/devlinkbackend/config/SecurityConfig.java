package com.mtvs.devlinkbackend.config;

import com.mtvs.devlinkbackend.oauth2.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public SecurityConfig(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oauth2UserService())
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler()) // 성공 핸들러 추가
                );
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

            // OAuth2AuthorizedClient를 사용하여 액세스 토큰과 리프레시 토큰 가져오기
            String clientRegistrationId = ((OAuth2UserRequest) authentication.getDetails()).getClientRegistration().getRegistrationId();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthUser.getName());

            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null;

            // 액세스 토큰 쿠키 설정
            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 60); // 1시간

            // 리프레시 토큰 쿠키 설정
            if (refreshToken != null) {
                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setMaxAge(3 * 60 * 60); // 3시간
                response.addCookie(refreshTokenCookie);
            }

            response.addCookie(accessTokenCookie);

            if(userService.findUserByAccessToken(accessToken) != null) { // 가입 했던 적이 있는지 확인
                userService.registUserByAccessToken(accessToken);
                response.sendRedirect("/user/info"); // 추가 정보 입력 페이지로 이동
            } else {
                response.sendRedirect("/"); // 가입했던 적이 있다면 홈으로 redirect
            }
        };
    }
}