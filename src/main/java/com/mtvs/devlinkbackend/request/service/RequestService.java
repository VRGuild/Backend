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
                requestRegistRequestDTO.getTitle(),
                requestRegistRequestDTO.getContent(),
                requestRegistRequestDTO.getStartDateTime(),
                requestRegistRequestDTO.getEndDateTime(),
                accountId
        ));
    }

    public Request findRequestByRequestId(Long requestId) {
        return requestRepository.findById(requestId).orElse(null);
    }

    public List<Request> findRequestsByAccountId(String accountId) {
        return requestRepository.findRequestsByAccountId(accountId);
    }

    public List<Request> findAllRequestsBetweenStarDateTimeAndEndDateTime(LocalDateTime starDateTime, LocalDateTime endDateTime) {
        return requestRepository.findRequestsWithinDateRange(starDateTime, endDateTime);
    }

    @Transactional
    public Request updateRequest(RequestUpdateRequestDTO requestUpdateRequestDTO, String accountId) {
        Optional<Request> request = requestRepository.findById(requestUpdateRequestDTO.getRequestId());
        if (request.isPresent()) {
            Request foundRequest = request.get();
            if(foundRequest.getAccountId().equals(accountId)) {
                foundRequest.setTitle(requestUpdateRequestDTO.getTitle());
                foundRequest.setContent(requestUpdateRequestDTO.getContent());
                foundRequest.setStartDateTime(requestUpdateRequestDTO.getStartDateTime());
                foundRequest.setEndDateTime(requestUpdateRequestDTO.getEndDateTime());
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

