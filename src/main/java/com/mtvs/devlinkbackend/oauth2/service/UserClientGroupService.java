package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.request.UserClientGroupRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientGroupListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientGroupSingleResponseDTO;
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
    public UserClientGroupSingleResponseDTO registUserClientGroup(UserClientGroupRequestDTO userClientGroupRequestDTO,
                                                                  String accountId) {
        return new UserClientGroupSingleResponseDTO(userClientGroupRepository.save(new UserClientGroup(
                accountId,
                userClientGroupRequestDTO.getPurpose(),
                userClientGroupRequestDTO.getClientType(),
                userClientGroupRequestDTO.getGroupName(),
                userClientGroupRequestDTO.getManagerName(),
                userClientGroupRequestDTO.getManagerPhone()
        )));
    }
    public UserClientGroupSingleResponseDTO findUserClientGroupByAccountId(String accountId) {
        return new UserClientGroupSingleResponseDTO(userClientGroupRepository.findUserClientGroupByAccountId(accountId));

    };
    public UserClientGroupListResponseDTO findByManagerNameContainingIgnoreCase(String managerName) {
        return new UserClientGroupListResponseDTO(
                userClientGroupRepository.findByManagerNameContainingIgnoreCase(managerName));

    };
    public UserClientGroupListResponseDTO findByGroupNameContainingIgnoreCase(String groupName) {
        return new UserClientGroupListResponseDTO(userClientGroupRepository.findByGroupNameContainingIgnoreCase(groupName));

    };
    public UserClientGroupListResponseDTO findByClientType(String clientType) {
        return new UserClientGroupListResponseDTO(userClientGroupRepository.findByClientType(clientType));

    };
    public UserClientGroupListResponseDTO findByManagerPhone(String managerPhone) {
        return new UserClientGroupListResponseDTO(userClientGroupRepository.findByManagerPhone(managerPhone));

    };

    @Transactional
    public UserClientGroupSingleResponseDTO updateUserClientGroup(UserClientGroupRequestDTO userClientGroupRequestDTO,
                                                 String accountId) {

        UserClientGroup userClientGroup = userClientGroupRepository.findUserClientGroupByAccountId(accountId);
        if (userClientGroup == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");

        userClientGroup.setClientType(userClientGroupRequestDTO.getClientType());
        userClientGroup.setGroupName(userClientGroup.getGroupName());
        userClientGroup.setManagerName(userClientGroup.getManagerName());
        userClientGroup.setManagerPhone(userClientGroup.getManagerPhone());

        return new UserClientGroupSingleResponseDTO(userClientGroup);
    }

    public void deleteByAccountId(String accountId) {
        userClientGroupRepository.deleteByAccountId(accountId);
    }
}
