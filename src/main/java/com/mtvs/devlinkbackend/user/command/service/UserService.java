package com.mtvs.devlinkbackend.user.command.service;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insertInitialUserInfo(String accountId) {
        return userRepository.save(new User(
                accountId,
                null,
                null,
                null
        ));
    }
    public Long findUserId(String accountId) {
        User user = userRepository.findByEpicAccountId(accountId);
        return user.getUserId();
    }
    public String findUserNickname(Long userId) {
        User user = userRepository.findNicknameByUserId(userId);
        return user.getNickname();
    }
}
