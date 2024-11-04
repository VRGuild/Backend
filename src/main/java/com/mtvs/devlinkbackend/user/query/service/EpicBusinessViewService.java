package com.mtvs.devlinkbackend.user.query.service;

import com.mtvs.devlinkbackend.user.query.model.dto.response.BusinessSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.repository.BusinessViewRepository;
import org.springframework.stereotype.Service;

@Service
public class EpicBusinessViewService {
    private final BusinessViewRepository businessViewRepository;

    public EpicBusinessViewService(BusinessViewRepository businessViewRepository) {
        this.businessViewRepository = businessViewRepository;
    }

    public BusinessSingleResponseDTO findBusinessByBusinessId(Long businessId) {
        return new BusinessSingleResponseDTO(businessViewRepository.findById(businessId).orElse(null));
    }
}
