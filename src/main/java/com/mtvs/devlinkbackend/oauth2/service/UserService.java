package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.oauth2.dto.UserUpdateRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User registUserByAccessToken(String accessToken) {
        try {
            String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(accessToken);
            return userRepository.save(new User(
                    accountId
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User findUserByAuthorizationHeader(String authorizationHeader) {
        try {
            String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(extractToken(authorizationHeader));
            return userRepository.findUserByAccountId(accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserByAccessToken(String accessToken, UserUpdateRequestDTO userUpdateRequestDTO) {
        try {
            String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(accessToken);
            User user = userRepository.findUserByAccountId(accountId);
            if(user != null) {
                user.setEmail(userUpdateRequestDTO.getEmail());
                user.setUserName(userUpdateRequestDTO.getUserName());
            }
            else throw new RuntimeException("user not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserByAccessToken(String accessToken) {
        try {
            String accountId = jwtUtil.getSubjectFromTokenWithAuth(accessToken);
            userRepository.deleteUserByAccountId(accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Authorization header must start with 'Bearer '");
        }
    }
}
