package com.mtvs.devlinkbackend.request.service;

import com.mtvs.devlinkbackend.request.dto.RequestRegistRequestDTO;
import com.mtvs.devlinkbackend.request.dto.RequestUpdateRequestDTO;
import com.mtvs.devlinkbackend.request.entity.Request;
import com.mtvs.devlinkbackend.request.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    public Request registRequest(RequestRegistRequestDTO requestRegistRequestDTO, String accountId) {
        return requestRepository.save(new Request(
                requestRegistRequestDTO.getWorkScope(),
                requestRegistRequestDTO.getWorkType(),
                requestRegistRequestDTO.getProgressClassification(),
                requestRegistRequestDTO.getCompanyName(),
                requestRegistRequestDTO.getTitle(),
                requestRegistRequestDTO.getContent(),
                requestRegistRequestDTO.getRequiredClient(),
                requestRegistRequestDTO.getRequiredServer(),
                requestRegistRequestDTO.getRequiredDesign(),
                requestRegistRequestDTO.getRequiredPlanner(),
                requestRegistRequestDTO.getRequiredAIEngineer(),
                requestRegistRequestDTO.getStartDateTime(),
                requestRegistRequestDTO.getEndDateTime(),
                requestRegistRequestDTO.getEstimatedCost(),
                accountId
        ));
    }

    public Request findRequestByRequestId(Long requestId) {
        return requestRepository.findById(requestId).orElse(null);
    }

    public List<Request> findRequestsByAccountId(String accountId) {
        return requestRepository.findRequestsByAccountId(accountId);
    }

    public List<Request> findRequestsByWorkScope(String workScope) {
        return requestRepository.findRequestsByWorkScope(workScope);
    }

    public List<Request> findRequestsByWorkType(String workType) {
        return requestRepository.findRequestsByWorkType(workType);
    }

    public List<Request> findRequestsByProgressClassification(String progressClassification) {
        return requestRepository.findRequestsByProgressClassification(progressClassification);
    }

    public List<Request> findRequestsByTitleContainingIgnoreCase(String title) {
        return requestRepository.findRequestsByTitleContainingIgnoreCase(title);
    }

    public List<Request> findRequestsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(
            LocalDateTime starDateTime, LocalDateTime endDateTime) {
        return requestRepository.findRequestsByStartDateTimeLessThanEqualOrEndDateTimeGreaterThanEqual(starDateTime, endDateTime);
    }

    public List<Request> findRequestsWithLargerRequirements(
            Integer requiredClient, Integer requiredServer, Integer requiredDesign,
            Integer requiredPlanner, Integer requiredAIEngineer) {

        return requestRepository.findRequestsWithLargerRequirements(
                requiredClient, requiredServer, requiredDesign, requiredPlanner, requiredAIEngineer);
    }

    @Transactional
    public Request updateRequest(RequestUpdateRequestDTO requestUpdateRequestDTO, String accountId) {
        Optional<Request> request = requestRepository.findById(requestUpdateRequestDTO.getRequestId());
        if (request.isPresent()) {
            Request foundRequest = request.get();
            if(foundRequest.getAccountId().equals(accountId)) {
                foundRequest.setWorkScope(requestUpdateRequestDTO.getWorkScope());
                foundRequest.setWorkType(requestUpdateRequestDTO.getWorkType());
                foundRequest.setProgressClassification(requestUpdateRequestDTO.getProgressClassification());
                foundRequest.setTitle(requestUpdateRequestDTO.getTitle());
                foundRequest.setContent(requestUpdateRequestDTO.getContent());
                foundRequest.setRequiredClient(requestUpdateRequestDTO.getRequiredClient());
                foundRequest.setRequiredServer(requestUpdateRequestDTO.getRequiredServer());
                foundRequest.setRequiredDesign(requestUpdateRequestDTO.getRequiredDesign());
                foundRequest.setRequiredPlanner(requestUpdateRequestDTO.getRequiredPlanner());
                foundRequest.setRequiredAIEngineer(requestUpdateRequestDTO.getRequiredAIEngineer());
                foundRequest.setStartDateTime(requestUpdateRequestDTO.getStartDateTime());
                foundRequest.setEndDateTime(requestUpdateRequestDTO.getEndDateTime());
                foundRequest.setEstimatedCost(requestUpdateRequestDTO.getEstimatedCost());
                return foundRequest;
            }
            else throw new IllegalArgumentException("잘못된 accountId로 Request ID : "
                    + requestUpdateRequestDTO.getRequestId() + "를 수정 시도");
        }
        else throw new IllegalArgumentException("잘못된 requestId로 수정 시도");
    }

    public void deleteRequest(Long requestId) {
        requestRepository.deleteById(requestId);
    }
}

