package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientGroupConvertRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientGroup;
import com.mtvs.devlinkbackend.oauth2.repository.UserClientGroupRepository;
import com.mtvs.devlinkbackend.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserClientGroupService {
    private final UserClientGroupRepository userClientGroupRepository;
    private final JwtUtil jwtUtil;

    public UserClientGroupService(UserClientGroupRepository userClientGroupRepository, JwtUtil jwtUtil) {
        this.userClientGroupRepository = userClientGroupRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserClientGroup registUserClientGroup(UserClientGroupConvertRequestDTO userClientGroupConvertRequestDTO,
                                                 String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return userClientGroupRepository.save(new UserClientGroup(
                accountId,
                userClientGroupConvertRequestDTO.getPurpose(),
                userClientGroupConvertRequestDTO.getClientType(),
                userClientGroupConvertRequestDTO.getGroupName(),
                userClientGroupConvertRequestDTO.getManagerName(),
                userClientGroupConvertRequestDTO.getManagerPhone()
        ));
    }
    public UserClientGroup findUserClientGroupByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        return userClientGroupRepository.findUserClientGroupByAccountId(accountId);

    };
    public List<UserClientGroup> findByManagerNameContainingIgnoreCase(String managerName) {
        return userClientGroupRepository.findByManagerNameContainingIgnoreCase(managerName);

    };
    public List<UserClientGroup> findByGroupNameContainingIgnoreCase(String groupName) {
        return userClientGroupRepository.findByGroupNameContainingIgnoreCase(groupName);

    };
    public List<UserClientGroup> findByClientType(String clientType) {
        return userClientGroupRepository.findByClientType(clientType);

    };
    public List<UserClientGroup> findByManagerPhone(String managerPhone) {
        return userClientGroupRepository.findByManagerPhone(managerPhone);

    };

    @Transactional
    public UserClientGroup updateUserClientGroup(UserClientGroupConvertRequestDTO userClientGroupConvertRequestDTO,
                                                 String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserClientGroup userClientGroup = userClientGroupRepository.findUserClientGroupByAccountId(accountId);
        if (userClientGroup == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");

        userClientGroup.setClientType(userClientGroupConvertRequestDTO.getClientType());
        userClientGroup.setGroupName(userClientGroup.getGroupName());
        userClientGroup.setManagerName(userClientGroup.getManagerName());
        userClientGroup.setManagerPhone(userClientGroup.getManagerPhone());

        return userClientGroup;
    }

    public void deleteByAuthorizationHeader(String authorizationHeader) throws Exception {
        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userClientGroupRepository.deleteByAccountId(accountId);
    }
}
