package com.mtvs.devlinkbackend.user.query.service;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserViewRepository userViewRepository;

    public UserService(JwtUtil jwtUtil, UserViewRepository userViewRepository) {
        this.jwtUtil = jwtUtil;
        this.userViewRepository = userViewRepository;
    }

    public User findUserByEpicAccountId(String authorizationHeader) throws Exception {
        String epicAccountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return userViewRepository.findUserByEpicAccountId(epicAccountId);
    }
}
