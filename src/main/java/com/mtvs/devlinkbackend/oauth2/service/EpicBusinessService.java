package com.mtvs.devlinkbackend.oauth2.service;

import com.mtvs.devlinkbackend.oauth2.dto.request.BusinessRequestDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientGroupListResponseDTO;
import com.mtvs.devlinkbackend.oauth2.dto.response.UserClientGroupSingleResponseDTO;
import com.mtvs.devlinkbackend.oauth2.entity.Business;
import com.mtvs.devlinkbackend.oauth2.entity.User;
import com.mtvs.devlinkbackend.oauth2.repository.BusinessRepository;
import com.mtvs.devlinkbackend.oauth2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpicBusinessService {
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public EpicBusinessService(BusinessRepository businessRepository, UserRepository userRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserClientGroupSingleResponseDTO registUserClientGroup(BusinessRequestDTO businessRequestDTO,
                                                                  String accountId) {

        User user = userRepository.findUserByEpicAccountId(accountId);
        if (user == null) {
            user = new User(
                    accountId,
                    null,
                    null,
                    businessRequestDTO.getNickname()
            );
        }

        Business business = new Business(
                businessRequestDTO.getBusinessName(),
                businessRequestDTO.getBusinessLogoImg().getOriginalFilename(),
                businessRequestDTO.getManagerName(),
                businessRequestDTO.getManagerPhone(),
                user
        );

        user.setBusiness(business);

        userRepository.save(user);

        // Response 나오면 바로 refactoring
        return new UserClientGroupSingleResponseDTO(businessRepository.save(business));
    }

    @Transactional
    public UserClientGroupSingleResponseDTO updateUserClientGroup(BusinessRequestDTO businessRequestDTO,
                                                                  String accountId) {

        User user = userRepository.findUserByEpicAccountId(accountId);
        Business business = businessRepository.findBusinessByUser_EpicAccountId(accountId);
        if (business == null || user == null)
            throw new IllegalArgumentException("잘못된 계정으로 그룹 정보 수정 시도");

        user.setNickname(businessRequestDTO.getNickname());
        business.setBusinessName(businessRequestDTO.getBusinessName());
        business.setBusinessLogoUrl(businessRequestDTO.getBusinessLogoImg().getOriginalFilename());
        business.setManagerName(businessRequestDTO.getManagerName());
        business.setManagerPhone(businessRequestDTO.getManagerPhone());

        return new UserClientGroupSingleResponseDTO(business);
    }

    public void deleteByAccountId(String accountId) {
        businessRepository.deleteByUser_EpicAccountId(accountId);
    }
}
