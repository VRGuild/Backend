package com.mtvs.devlinkbackend.user.command.service;

import com.mtvs.devlinkbackend.user.command.model.dto.request.BusinessRequestDTO;
import com.mtvs.devlinkbackend.user.query.model.dto.response.UserClientGroupSingleResponseDTO;
import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.command.repository.BusinessRepository;
import com.mtvs.devlinkbackend.user.command.repository.UserRepository;
import com.mtvs.devlinkbackend.user.query.repository.BusinessViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpicBusinessService {
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final UserViewRepository userViewRepository;
    private final BusinessViewRepository businessViewRepository;


    public EpicBusinessService(BusinessRepository businessRepository, UserRepository userRepository, UserViewRepository userViewRepository, BusinessViewRepository businessViewRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.userViewRepository = userViewRepository;
        this.businessViewRepository = businessViewRepository;
    }

    @Transactional
    public UserClientGroupSingleResponseDTO registUserClientGroup(BusinessRequestDTO businessRequestDTO,
                                                                  String accountId) {

        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null) {
            user = new User(
                    accountId,
                    null,
                    null,
                    businessRequestDTO.getNickname()
            );
        }
        User savedUser = userRepository.save(user);


        Business business = new Business(
                businessRequestDTO.getBusinessName(),
                businessRequestDTO.getBusinessLogoImg().getOriginalFilename(),
                businessRequestDTO.getManagerName(),
                businessRequestDTO.getManagerPhone(),
                savedUser.getUserId()
        );
        Business savedBusiness = businessRepository.save(business);

        savedUser.setBusinessId(savedBusiness.getBusinessId());


        // Response 나오면 바로 refactoring
        return new UserClientGroupSingleResponseDTO(businessRepository.save(business));
    }

    @Transactional
    public UserClientGroupSingleResponseDTO updateUserClientGroup(BusinessRequestDTO businessRequestDTO,
                                                                  String accountId) {

        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 Business 수정 시도");
        Business business = businessViewRepository.findBusinessByUserId(user.getUserId());
        if (business == null)
            throw new IllegalArgumentException("잘못된 계정으로 Business 수정 시도");

        user.setNickname(businessRequestDTO.getNickname());
        business.setBusinessName(businessRequestDTO.getBusinessName());
        business.setBusinessLogoUrl(businessRequestDTO.getBusinessLogoImg().getOriginalFilename());
        business.setManagerName(businessRequestDTO.getManagerName());
        business.setManagerPhone(businessRequestDTO.getManagerPhone());

        return new UserClientGroupSingleResponseDTO(business);
    }

    public void deleteByAccountId(String accountId) {
        User user = userViewRepository.findUserByEpicAccountId(accountId);
        if (user == null)
            throw new IllegalArgumentException("잘못된 계정으로 Business 삭제 시도");
        businessRepository.deleteByUserId(user.getUserId());
    }
}
