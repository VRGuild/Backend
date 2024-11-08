package com.mtvs.devlinkbackend.user.command.service;

import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserSingleResponseDTO;
import org.springframework.stereotype.Service;

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
}
