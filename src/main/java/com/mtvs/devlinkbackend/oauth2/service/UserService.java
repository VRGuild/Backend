package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.repository.UserRepository;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public User findUserByEpicAccountId(String authorizationHeader) throws Exception {
        String epicAccountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return userRepository.findUserByEpicAccountId(epicAccountId);
    }
}
