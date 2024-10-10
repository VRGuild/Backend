package com.mtvs.devlinkbackend.config;

import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserService userService, CorsConfigurationSource corsConfigurationSource, OAuth2AuthorizedClientService authorizedClientService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.corsConfigurationSource = corsConfigurationSource;
        this.authorizedClientService = authorizedClientService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) // CORS 설정
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/v3/api-docs/**",                // Swagger API Docs 경로
                                "/swagger-ui/**",                 // Swagger UI 정적 리소스 경로
                                "/swagger-ui.html",               // Swagger UI 페이지 경로
                                "/swagger-resources/**",          // Swagger 관련 리소스 경로
                                "/webjars/**",                    // Webjars로 제공되는 Swagger 리소스 경로
                                "/configuration/ui",              // 추가 Swagger 설정 경로
                                "/configuration/security",        // 추가 Swagger 설정 경로
                                "/error"                          // 오류 페이지 경로 허용
                        ).permitAll() // Swagger 관련 경로 허용
                        .anyRequest().authenticated()
                )
                // oauth2Login 설정이 다른 경로에서만 작동하도록 설정
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oauth2UserService())
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler())
                        .logoutSuccessUrl("/")
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 가능성 :
    // "/swagger" 경로로 들어온 요청은 필터에서 permitAll로 권한 인증을 통과했지만
    // "index.html"로 서블릿 포워딩 되면서 시큐리티 필터를 다시 거치게 되는데 권한 없음으로 403이 내려오는 것이다.
    // 요청 한번에 권한 인증이 여러번 되는 이유는 직접 구현한 토큰 인증 필터와 같은 경우 OncePerRequestFilter를 확장해서
    // 요청 당 한번만 거치도록 설정하지만 기본적으로 필터는 서블릿에 대한 매요청마다 거치는 것이 기본 전략임

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            System.out.println("여기 오냐");
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

            // OAuth2AuthorizedClient를 사용하여 액세스 토큰과 리프레시 토큰 가져오기
            String clientRegistrationId = ((OAuth2UserRequest) authentication.getDetails()).getClientRegistration().getRegistrationId();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthUser.getName());

            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null;

            User foundUser = userService.findUserByAccessToken(accessToken);
            if(foundUser == null) { // 가입 했던 적이 있는지 확인
                foundUser = userService.registUserByAccessToken(accessToken);
                foundUser.setRefreshToken(refreshToken);
                response.sendRedirect("/user/info"); // 추가 정보 입력 페이지로 이동
            } else {
                foundUser.setRefreshToken(refreshToken);
                response.sendRedirect("/"); // 가입했던 적이 있다면 홈으로 redirect
            }
        };
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            // 쿠키 삭제
            deleteCookie(response, "access_token");
            deleteCookie(response, "refresh_token");
        };
    }

    private void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 즉시 삭제
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}