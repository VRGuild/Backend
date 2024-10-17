package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientIndividualConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientIndividual;
import com.mtvs.devlinkbackend.oauth2.repository.UserClientIndividualRepository;
import com.mtvs.devlinkbackend.oauth2.repository.UserRepository;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserClientIndividualService {

    private final UserClientIndividualRepository userClientIndividualRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserClientIndividualService(UserClientIndividualRepository userClientIndividualRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userClientIndividualRepository = userClientIndividualRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public UserClientIndividual findUserClientIndividualByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return userClientIndividualRepository.findUserClientIndividualByAccountId(accountId);
    };

    public List<UserClientIndividual> findUserClientIndividualsByNameContainingIgnoreCase(String name) {
        return userClientIndividualRepository.findUserClientIndividualsByNameContainingIgnoreCase(name);
    };

    public List<UserClientIndividual> findUserClientIndividualsByPhone(String phone) {
        return userClientIndividualRepository.findUserClientIndividualsByPhone(phone);
    };

    @Transactional
    public UserClientIndividual convertUserToUserClientIndividual(UserClientIndividualConvertRequestDTO userClientIndividualConvertRequestDTO,
                                                                  String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        User user = userRepository.findUserByAccountId(accountId);
        if(user == null)
            throw new IllegalArgumentException("잘못된 계정으로 추가 정보 입력 중");

        userRepository.delete(user);
        return userClientIndividualRepository.save(new UserClientIndividual(
                user.getUserId(),
                user.getAccountId(),
                userClientIndividualConvertRequestDTO.getPurpose(),
                userClientIndividualConvertRequestDTO.getName(),
                userClientIndividualConvertRequestDTO.getPhone()
        ));
    }

    @Transactional
    public UserClientIndividual updateUserClientIndividual(UserClientIndividualConvertRequestDTO userClientIndividualConvertRequestDTO,
                                                           String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientIndividual userClientIndividual = userClientIndividualRepository.findUserClientIndividualByAccountId(accountId);
        if(userClientIndividual == null)
            throw new IllegalArgumentException("잘못된 계정으로 개인 정보 수정 시도");

        userClientIndividual.setName(userClientIndividualConvertRequestDTO.getName());
        userClientIndividual.setPhone(userClientIndividualConvertRequestDTO.getPhone());

        return userClientIndividual;
    }

    public void deleteByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userClientIndividualRepository.deleteByAccountId(accountId);
    };
}
