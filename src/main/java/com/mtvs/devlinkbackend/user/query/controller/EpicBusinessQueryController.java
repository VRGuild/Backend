package com.mtvs.devlinkbackend.user.query.controller;

import com.mtvs.devlinkbackend.user.query.model.dto.response.BusinessSingleResponseDTO;
import com.mtvs.devlinkbackend.user.query.service.EpicBusinessViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/epic/businesses")
public class EpicBusinessQueryController {

    private final EpicBusinessViewService epicBusinessViewService;

    public EpicBusinessQueryController(EpicBusinessViewService epicBusinessViewService) {
        this.epicBusinessViewService = epicBusinessViewService;
    }

    @Operation(summary = "Epic 계정으로 자기 Dev 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "401", description = "잘못된 토큰")
    })
    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessSingleResponseDTO> findDevByEpicAccount(
            @PathVariable(name = "businessId") Long businessId) throws Exception {

        BusinessSingleResponseDTO businessSingleResponseDTO = epicBusinessViewService.findBusinessByBusinessId(businessId);
        return ResponseEntity.ok(businessSingleResponseDTO);
    }
}
