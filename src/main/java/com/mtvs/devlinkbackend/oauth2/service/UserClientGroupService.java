package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.UserClientGroupRequestDTO;
import com.mtvs.devlinkbackend.oauth2.entity.UserClientGroup;
import com.mtvs.devlinkbackend.oauth2.repository.UserClientGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserClientGroupService {
    private final UserClientGroupRepository userClientGroupRepository;

    public UserClientGroupService(UserClientGroupRepository userClientGroupRepository) {
        this.userClientGroupRepository = userClientGroupRepository;
    }

    @Transactional
    public UserClientGroup registUserClientGroup(UserClientGroupRequestDTO userClientGroupRequestDTO,
                                                 String accountId) {
        return userClientGroupRepository.save(new UserClientGroup(
                accountId,
                userClientGroupRequestDTO.getPurpose(),
                userClientGroupRequestDTO.getClientType(),
                userClientGroupRequestDTO.getGroupName(),
                userClientGroupRequestDTO.getManagerName(),
                userClientGroupRequestDTO.getManagerPhone()
        ));
    }
    public UserClientGroup findUserClientGroupByAccountId(String accountId) {
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
    public UserClientGroup updateUserClientGroup(UserClientGroupRequestDTO userClientGroupRequestDTO,
                                                 String accountId) {

        UserClientGroup userClientGroup = userClientGroupRepository.findUserClientGroupByAccountId(accountId);
        if (userClientGroup == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");

        userClientGroup.setClientType(userClientGroupRequestDTO.getClientType());
        userClientGroup.setGroupName(userClientGroup.getGroupName());
        userClientGroup.setManagerName(userClientGroup.getManagerName());
        userClientGroup.setManagerPhone(userClientGroup.getManagerPhone());

        return userClientGroup;
    }

    public void deleteByAccountId(String accountId) {
        userClientGroupRepository.deleteByAccountId(accountId);
    }
}
