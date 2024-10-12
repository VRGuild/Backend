package com.mtvs.devlinkbackend.request.controller;

import com.mtvs.devlinkbackend.request.dto.RequestRegistRequestDTO;
import com.mtvs.devlinkbackend.request.dto.RequestUpdateRequestDTO;
import com.mtvs.devlinkbackend.request.entity.Request;
import com.mtvs.devlinkbackend.request.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Operation(summary = "새로운 의뢰 등록", description = "새로운 의뢰를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "의뢰가 성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    @PostMapping
    public ResponseEntity<Request> registerRequest(@RequestBody RequestRegistRequestDTO requestDTO, @RequestParam String accountId) {
        Request newRequest = requestService.registRequest(requestDTO, accountId);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    @Operation(summary = "특정 의뢰 조회", description = "ID로 특정 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "의뢰가 성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "의뢰를 찾을 수 없음")
    })
    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long requestId) {
        Request request = requestService.findRequestByRequestId(requestId);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "계정별 의뢰 목록 조회", description = "특정 계정에 대한 모든 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/account")
    public ResponseEntity<List<Request>> getRequestsByAccountId(@RequestParam String accountId) {
        List<Request> requests = requestService.findRequestsByAccountId(accountId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "특정 기간 내의 의뢰 목록 조회", description = "시작과 끝 날짜 사이의 모든 의뢰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "의뢰 목록이 성공적으로 조회됨")
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<Request>> getRequestsBetweenDates(@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        List<Request> requests = requestService.findAllRequestsBetweenStarDateTimeAndEndDateTime(startDateTime, endDateTime);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "의뢰 업데이트", description = "기존 의뢰를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "의뢰가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 또는 권한 없음")
    })
    @PutMapping
    public ResponseEntity<Request> updateRequest(@RequestBody RequestUpdateRequestDTO requestDTO, @RequestParam String accountId) {
        try {
            Request updatedRequest = requestService.updateRequest(requestDTO, accountId);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "의뢰 삭제", description = "ID로 특정 의뢰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "의뢰가 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "의뢰를 찾을 수 없음")
    })
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.noContent().build();
    }
}
