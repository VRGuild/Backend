package com.mtvs.devlinkbackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/", "/login").permitAll() // 새로운 메서드 사용
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/", true) // 로그인 성공 후 리디렉션
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(oauth2UserService()) // 커스텀 유저 서비스
                )
            );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oauth2User = super.loadUser(userRequest);

                // 사용자 정보 가져오기
                Map<String, Object> attributes = oauth2User.getAttributes();
                String userId = (String) attributes.get("sub"); // "sub"는 Epic Games 사용자 ID
                String name = oauth2User.getAttribute("name");
                String email = oauth2User.getAttribute("email");

                // 필요한 경우 추가 로직 구현 (사용자 등록, 데이터베이스 연동 등)

                // 데이터베이스에서 sub로 사용자 조회
//                User user = userRepository.findBySub(sub);
//
//                if (user == null) {
//                    // 새로운 사용자인 경우 저장
//                    user = new User(sub, name, email);
//                    userRepository.save(user);
//                } else {
//                    // 기존 사용자의 정보 업데이트 (필요 시)
//                    user.setName(name);
//                    user.setEmail(email);
//                    userRepository.save(user);
//                }

                Set<OAuth2UserAuthority> authorities = new HashSet<>();
                authorities.add(new OAuth2UserAuthority(attributes));

                // OAuth2User 객체 반환 (사용자 정보 포함)
                return new DefaultOAuth2User(authorities, attributes, "sub");
            }
        };
    }
}
