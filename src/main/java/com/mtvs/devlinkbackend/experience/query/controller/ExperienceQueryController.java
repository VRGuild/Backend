package com.mtvs.devlinkbackend.experience.query.controller;

import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceListResponseDTO;
import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceSingleResponseDTO;
import com.mtvs.devlinkbackend.experience.query.service.ExperienceViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experience")
public class ExperienceQueryController {

    private final ExperienceViewService experienceViewService;

    public ExperienceQueryController(ExperienceViewService experienceViewService) {
        this.experienceViewService = experienceViewService;
    }

    @Operation(summary = "Experience ID로 Ether 조회", description = "Experience ID를 사용하여 활동내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없음")
    })
    @GetMapping("/{expId}")
    public ResponseEntity<ExperienceSingleResponseDTO> findExperienceByExpId(@PathVariable Long expId) {
        ExperienceSingleResponseDTO experience = experienceViewService.findExperienceByExpId(expId);
        return experience != null ? ResponseEntity.ok(experience) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "사용자 ID로 활동 내역 목록 조회", description = "사용자 ID를 사용하여 관련된 모든 활동 내역들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 찾음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @GetMapping("/{userId}/list/{page}")
    public ResponseEntity<ExperienceListResponseDTO> findExperiencesByUserId(
            @PathVariable(name = "userId") Long userId, @PathVariable(name = "page") Integer page) {

        ExperienceListResponseDTO ethers = experienceViewService.findExperiencesByUserId(userId, page);
        return ResponseEntity.ok(ethers);
    }
}
